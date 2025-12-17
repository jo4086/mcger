import fs from 'fs';
import { resolve } from 'path';

import { app } from 'electron';

import type { ServerInfo } from '@/types/server';

function ensureDir(path: string) {
  if (!fs.existsSync(path)) {
    fs.mkdirSync(path, { recursive: true });
  }
}

export function getServersRoot() {
  return resolve(app.getPath('userData'), 'servers');
}

export function getCurrentPath() {
  return resolve(getServersRoot(), 'current.json');
}

export function getHistoryDir() {
  return resolve(getServersRoot(), 'history');
}

export function getMetaPath() {
  return resolve(getServersRoot(), 'meta.json');
}

export function getSeedPath() {
  // 고정값: 앱에 포함된 seed
  return resolve(__dirname, '../data/servers.json');
}

interface ServerMeta {
  latest: string;
  history: string[];
}

export function bootstrapServers() {
  ensureDir(getServersRoot());
  ensureDir(getHistoryDir());

  const currentPath = getCurrentPath();
  const metaPath = getMetaPath();

  if (fs.existsSync(currentPath)) {
    return; // 이미 사용자 데이터 있음 → 존중
  }

  const seed = fs.readFileSync(getSeedPath(), 'utf-8');
  const ts = new Date().toISOString().replace(/[:.]/g, '-');
  const historyFile = `${ts}.json`;

  fs.writeFileSync(currentPath, seed);
  fs.writeFileSync(resolve(getHistoryDir(), historyFile), seed);

  const meta: ServerMeta = {
    latest: historyFile,
    history: [historyFile],
  };

  fs.writeFileSync(metaPath, JSON.stringify(meta, null, 2));
}

export function readServers(): ServerInfo[] {
  const raw = fs.readFileSync(getCurrentPath(), 'utf-8');
  return JSON.parse(raw) as ServerInfo[];
}

export function updateServers(next: ServerInfo[]) {
  const currentPath = getCurrentPath();
  const metaPath = getMetaPath();

  const prevRaw = fs.readFileSync(currentPath, 'utf-8');

  // 백업 생성
  const ts = new Date().toISOString().replace(/[:.]/g, '-');
  const historyFile = `${ts}.json`;

  fs.writeFileSync(resolve(getHistoryDir(), historyFile), prevRaw);

  // current 갱신
  fs.writeFileSync(currentPath, JSON.stringify(next, null, 2));

  // meta 갱신
  const meta: ServerMeta = fs.existsSync(metaPath) ? JSON.parse(fs.readFileSync(metaPath, 'utf-8')) : { latest: '', history: [] };

  meta.latest = historyFile;
  meta.history.push(historyFile);

  fs.writeFileSync(metaPath, JSON.stringify(meta, null, 2));
}
