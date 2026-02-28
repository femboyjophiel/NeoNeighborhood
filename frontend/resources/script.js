SERVER_URL = "https://neoneighborhood.jophiel.org";
SERVER_PORT = "";


const BOARD_SIZE = 32;
let tileSize = 32;
const layers = ["tiles", "decor", "overlay"];
let grid = [];


const land = document.getElementById("land");


const sheetPalettes = {}; 


function buildBoard() {
  land.innerHTML = "";
  land.style.gridTemplateColumns = `repeat(${BOARD_SIZE}, ${tileSize}px)`;
  land.style.gridTemplateRows = `repeat(${BOARD_SIZE}, ${tileSize}px)`;

  for (let y = 0; y < BOARD_SIZE; y++) {
    grid[y] = [];

    for (let x = 0; x < BOARD_SIZE; x++) {
      grid[y][x] = { tiles: null, decor: null, overlay: null };

      const tile = document.createElement("div");
      tile.className = "tile";
      tile.dataset.x = x;
      tile.dataset.y = y;
      tile.style.width = tileSize + "px";
      tile.style.height = tileSize + "px";

      layers.forEach(l => {
        const layerDiv = document.createElement("div");
        layerDiv.className = `layer ${l}`;
        tile.appendChild(layerDiv);
      });

      land.appendChild(tile);
    }
  }
}


function loadSpriteSheet(sheetName, sizePx) {
  return new Promise(resolve => {
    const img = new Image();
    img.src = `/map/${sheetName}`;
    img.onload = () => {
      const cols = Math.floor(img.width / sizePx);
      const rows = Math.floor(img.height / sizePx);
      const tiles = [];
      const canvas = document.createElement("canvas");
      canvas.width = canvas.height = sizePx;
      const ctx = canvas.getContext("2d");

      for (let y = 0; y < rows; y++) {
        for (let x = 0; x < cols; x++) {
          ctx.clearRect(0, 0, sizePx, sizePx);
          ctx.drawImage(img, x * sizePx, y * sizePx, sizePx, sizePx, 0, 0, sizePx, sizePx);
          const url = canvas.toDataURL();
          
          const tileName = `${sheetName}_${y}_${x}`; 
          tiles.push({ name: tileName, url });
        }
      }

      sheetPalettes[sheetName] = tiles;
      resolve();
    };
  });
}

function renderMap(data) {
  data.grid.forEach((row, y) => {
    row.forEach((cell, x) => {
      layers.forEach(l => {
        const tileValue = cell[l];
        if (!tileValue) return;

        const tileDiv = document.querySelector(`.tile[data-x="${x}"][data-y="${y}"]`);
        const layerDiv = tileDiv.querySelector(`.${l}`);

        const sheetTile = Object.values(sheetPalettes).flat()
          .find(t => t.name === tileValue);

        if (sheetTile) {
          layerDiv.style.backgroundImage = `url(${sheetTile.url})`;
        }
      });
    });
  });
}


document.addEventListener("DOMContentLoaded", async () => {
  buildBoard();

  const sheets = ["1_terrain.png", "2_indoors.png", "3_plants.png", "4_buildings.png", "5_waterfall.png", "7_grass_cliff.png", "11_roofs.png", "12_extra1.png", "13_extra2.png"];
  await Promise.all(sheets.map(s => loadSpriteSheet(s, tileSize)));

  const res = await fetch(`${SERVER_URL}${SERVER_PORT}/api/map/tiles`);
  const mapData = await res.json();
  renderMap(mapData);
});