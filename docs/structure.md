project name: mcger

root: ~/projects/mcger

tree
.
├── apps/
│　　├── electronApp/
│　　│　　├── .env
│　　│　　├── .gitignore
│　　│　　├── .turbo
│　　│　　├── dist/
│　　│　　├── electron.vite.config.ts
│　　│　　├── index.html
│　　│　　├── node_modules/
│　　│　　├── out/
│　　│　　├── package.json
│　　│　　├── postcss.config.js
│　　│　　├── prisma/
│　　│　　├── prisma.config.ts
│　　│　　├── public/
│　　│　　├── README.md
│　　│　　├── scripts/
│　　│　　└── src/
│　　│　　 　　├── config/
│　　│　　 　　│　├── index.ts
│　　│　　 　　│　└── types.ts
│　　│　　 　　├── data/
│　　│　　 　　│　├── servers.json
│　　│　　 　　│　└── servers2.json
│　　│　　 　　├── del/
│　　│　　 　　│　├── App.css
│　　│　　 　　│　├── App.tsx
│　　│　　 　　│　├── index.css
│　　│　　 　　│　└── main.tsx
│　　│　　 　　├── main/
│　　│　　 　　│　　├── \_bak.index.ts
│　　│　　 　　│　　├── index.ts
│　　│　　 　　│　　└── main.ts
│　　│　　 　　├── preload/
│　　│　　 　　│　　├── \_bak.index.ts
│　　│　　 　　│　　├── index.ts
│　　│　　 　　│　　└── preload.ts
│　　│　　 　　├── renderer/
│　　│　　 　　│　　├── .env
│　　│　　 　　│　　├── index.html
│　　│　　 　　│　　└── src/
│　　│　　 　　│　　　　├── App.css
│　　│　　 　　│　　　　├── App.tsx
│　　│　　 　　│　　　　├── assets/
│　　│　　 　　│　　　　│　　└── react.svg
│　　│　　 　　│　　　　├── components/
│　　│　　 　　│　　　　│　　├── shared/
│　　│　　 　　│　　　　│　　│　　├── Button/
│　　│　　 　　│　　　　│　　│　　│　　├── index.tsx
│　　│　　 　　│　　　　│　　│　　│　　└── ToggleBtn.tsx
│　　│　　 　　│　　　　│　　│　　├── index.ts
│　　│　　 　　│　　　　│　　│　　└── List/
│　　│　　 　　│　　　　│　　│　　　　 ├── index.ts
│　　│　　 　　│　　　　│　　│　　　　 ├── List.tsx
│　　│　　 　　│　　　　│　　│　　　　 └── ListItem.tsx
│　　│　　 　　│　　　　│　　├── TabItem/
│　　│　　 　　│　　　　│　　│　　└── index.tsx
│　　│　　 　　│　　　　│　　└── Tabs/
│　　│　　 　　│　　　　│　　　　 └── index.tsx
│　　│　　 　　│　　　　├── global.d.ts
│　　│　　 　　│　　　　├── index.css
│　　│　　 　　│　　　　├── index.css.bak
│　　│　　 　　│　　　　├── layouts/
│　　│　　 　　│　　　　│　　├── Footer.tsx
│　　│　　 　　│　　　　│　　├── Header.tsx
│　　│　　 　　│　　　　│　　├── index.ts
│　　│　　 　　│　　　　│　　└── Layout.tsx
│　　│　　 　　│　　　　├── main.tsx
│　　│　　 　　│　　　　├── pages/
│　　│　　 　　│　　　　│　　├── Home/
│　　│　　 　　│　　　　│　　│　　├── index.tsx
│　　│　　 　　│　　　　│　　│　　└── ServerCard.tsx
│　　│　　 　　│　　　　│　　├── index.ts
│　　│　　 　　│　　　　│　　└── Main/
│　　│　　 　　│　　　　│　　　　 ├── Content.tsx
│　　│　　 　　│　　　　│　　　　 ├── index.tsx
│　　│　　 　　│　　　　│　　　　 ├── tabs/
│　　│　　 　　│　　　　│　　　　 │　　├── index.ts
│　　│　　 　　│　　　　│　　　　 │　　├── Logs.tsx
│　　│　　 　　│　　　　│　　　　 │　　├── Settings.tsx
│　　│　　 　　│　　　　│　　　　 │　　├── Status.tsx
│　　│　　 　　│　　　　│　　　　 │　　└── Users.tsx
│　　│　　 　　│　　　　│　　　　 └── types.ts
│　　│　　 　　│　　　　├── router/
│　　│　　 　　│　　　　│　　├── config.tsx
│　　│　　 　　│　　　　│　　├── index.ts
│　　│　　 　　│　　　　│　　└── navigation.ts
│　　│　　 　　│　　　　├── store/
│　　│　　 　　│　　　　│　　├── useAppConfigStore.ts
│　　│　　 　　│　　　　│　　├── useServerListStore.ts
│　　│　　 　　│　　　　│　　├── useServerRuntimeStore.ts
│　　│　　 　　│　　　　│　　└── useServerStore.ts
│　　│　　 　　│　　　　├── theme/
│　　│　　 　　│　　　　│　　└── custom.css
│　　│　　 　　│　　　　├── types/
│　　│　　 　　│　　　　│　　└── size.ts
│　　│　　 　　│　　　　└── utils/
│　　│　　 　　│　　　　　　 └── index.ts
│　　│　　 　　├── serverStore.ts
│　　│　　 　　└── types/
│　　│　　 　　　　 ├── electron.d.ts
│　　│　　 　　　　 ├── index.ts
│　　│　　 　　　　 └── server.ts
│　　└── watch/
├── docs/
├── eslintt.config.js
├── GEMINI.md
├── node_modules/
├── package.json
├── packages/
├── pnpm-lock.yaml
├── pnpm-workspace.yaml
├── README.md
├── tsconfig.base.json
└── turbo.json
