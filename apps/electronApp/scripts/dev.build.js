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
