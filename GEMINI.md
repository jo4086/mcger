# 마인크래프트의 서버 매니지먼트

## 개요

마인크래프트의 다중서버 운영을 위한 서버 매니지먼트로 rcon, cron, axios, design, typescript, electron, systemd service를 사용하며

서버운영을 위한 `apps/manager-core`
서버운영 GUI를 위한 `apps/manager-render`
서버로그기록을 통해 디코봇과 연동하여 메세지를 보내는 `apps/watch`

## 사용 기술

- `turborepo`, `vite-react`, `tsup`

## gemini 프로젝트 요약

- 프로젝트명: minecraft_management
- 설명: Minecraft 서버 관리용 데스크톱 애플리케이션
- 아키텍처: React 프론트엔드를 갖춘 Electron 애플리케이션으로, pnpm과 turbo로 관리되는 모노레포.
  - 메인 프로세스 (`manager-core`): Electron 앱의 백엔드. 핵심 로직, Prisma를 이용한 데이터베이스 연동, RCON을  
    통한 Minecraft 서버 통신, node-cron을 이용한 스케줄링 작업을 처리합니다.
  - 렌더러 프로세스 (`manager-render`): React와 Vite로 구축된 Electron 앱의 프론트엔드 UI.
  - Watcher (`watch`): 파일 변경 감지를 위한 개발용 유틸리티 프로세스.
- 공유 패키지:
  - @minecraft_management/server_core: 서버 사이드 로직 공유 코드.
  - @minecraft_management/types: 공유 TypeScript 타입 정의.
  - @minecraft_management/utils: 공유 유틸리티 함수.
  - @minecraft_management/config: ESLint, Prettier 등 공유 설정 파일.
- 사용된 도구:
  - 패키지 매니저: pnpm
  - 모노레포 도구: turbo
  - 언어: TypeScript
  - 번들러/빌더: 패키지용 tsup, React 앱용 vite.
  - 린팅/포매팅: ESLint, Prettier.
  - 데이터베이스: Prisma.
  - 데스크톱 프레임워크: Electron.
