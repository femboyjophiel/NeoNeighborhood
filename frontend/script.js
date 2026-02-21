const SERVER_URL = "http://localhost"
const SERVER_PORT = 8080

const MAP_LENGTHX = 16;
const MAP_LENGTHY = 16;

let defaultTile = "Plains";
const land = document.getElementById("land");

// Gets map.json in its entirety
function fetchMap() {
return fetch (`${SERVER_URL}:${SERVER_PORT}/api/map/tiles`) 
    .then(response => {
        if (!response.ok) throw new Error(response.statusText);
        return response.json();
    })

    .then(data => data.tiles);
}

function applyDefault(type) {
    for (const tile of land.children) {
        tile.classList.add(type);
    }
}

function renderMap(map) {
    map[0].forEach(tileSet => {
        console.log(tileSet)
        for (let x =0; x < tileSet.x.length; x++) {
            document.getElementById((tileSet.x[x]) + (tileSet.y[x]*16)).classList.remove(defaultTile);
            document.getElementById((tileSet.x[x]) + (tileSet.y[x]*16)).classList.add(tileSet.type);
        }
});
}

// This funciton creates the grid that tiles will placed on
// It is seperate from render because creating a ton of elements is expensive on older browsers
function createMapFoundation() {
    for (let x = 0; x < MAP_LENGTHX * MAP_LENGTHY; x++) {
            let div = document.createElement("div");
            div.classList.add(`x:${x%MAP_LENGTHX}`, `y:${Math.floor(x/MAP_LENGTHY)}`); //Add x and y seperately so rows/columns can be altered if need.
            div.id = x; //This will be used most often to target tiles specifically
            land.appendChild(div)
    }
}



createMapFoundation();
applyDefault(defaultTile);
fetchMap().then(map => {
    renderMap(map);
});

let isDown = false;
let startX;
let startY;
let scrollLeft;
let scrollTop;


land.addEventListener("mousedown", (e) => {
    isDown = true;
    land.classList.add("dragging");

    startX = e.pageX - land.offsetLeft;
    startY = e.pageY - land.offsetTop;

    scrollLeft = land.scrollLeft;
    scrollTop = land.scrollTop;

});

land.addEventListener("mouseleave", () => {
    isDown = false;
    land.classList.remove("dragging");
});

land.addEventListener("mouseup", () => {
    isDown = false;
    land.classList.remove("dragging");
});

land.addEventListener("mousemove", (e) => {
    if (!isDown) return;
    e.preventDefault();

    const x = e.pageX - land.offsetLeft;
    const y = e.pageY - land.offsetTop;

    const walkX = (x - startX);
    const walkY = (y - startY);

    land.scrollLeft = scrollLeft - walkX;
    land.scrollTop = scrollTop - walkY;

})