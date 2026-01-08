import fs from 'fs';
import path from 'path';

const fileContent = 'ELECTRON_RENDERER_URL=http://localhost:5173'; // 고정값
const fileName = '.env'; // 고정값

const __dirname = new URL('.', import.meta.url).pathname;
const filePath = path.resolve(__dirname, '../src/renderer'); // 예시

const fullPath = path.join(filePath, fileName);

fs.readFile(fullPath, 'utf-8', (err, data) => {
  if (err) {
    return fs.writeFile(fullPath, fileContent + '\n', 'utf-8', (e) => e && console.error(e));
  }

  if (!data.includes(fileContent)) {
    const contentToAppend = data.endsWith('\n') ? fileContent + '\n' : '\n' + fileContent + '\n';

    fs.appendFile(fullPath, contentToAppend, 'utf-8', (e) => e && console.error(e));
  }
});

const distPath = path.resolve(__dirname, '../dist/data'); // 예시
const serversJsonPath = path.join(distPath, 'servers.json');

// 예시 seed 데이터
const initialServers = [
  {
    id: 'server-a',
    name: 'A 서버',
    host: '127.0.0.1',
    port: 8080,
    'rcon-port': 25575,
    'server-port': 25565,
  },
  {
    id: 'server-b',
    name: 'B 서버',
    host: '127.0.0.1',
    port: 8081,
    'rcon-port': 25575,
    'server-port': 25565,
  },
  {
    id: 'server-c',
    name: 'C 서버',
    host: '127.0.0.1',
    port: 8082,
    'rcon-port': 25575,
    'server-port': 25565,
  },
];

// dist 폴더 없으면 생성
if (!fs.existsSync(distPath)) {
  fs.mkdirSync(distPath, { recursive: true });
}

// servers.json 없으면 생성
if (!fs.existsSync(serversJsonPath)) {
  fs.writeFileSync(serversJsonPath, JSON.stringify(initialServers, null, 2), 'utf-8');
}
