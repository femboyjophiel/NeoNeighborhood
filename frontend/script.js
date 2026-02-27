const SERVER_URL = "https://neoneighborhood.jophiel.org";
const SERVER_PORT = ""//":8080";

const MAP_LENGTHX = 32;
const MAP_LENGTHY = 32;

let defaultTile = "Plains";
const land = document.getElementById("land");

// Gets map.json in its entirety
function fetchMap() {
return fetch (`${SERVER_URL}${SERVER_PORT}/api/map/tiles`) 
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
function register() {
    let rusername = document.getElementById("register-username").value;
    let rpassword = document.getElementById("register-password").value;

fetch(`${SERVER_URL}:${SERVER_PORT}/api/user/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username: rusername, password: rpassword })
})
.then(response => {
    if (!response.ok) throw new Error(response.statusText);
    return response.json();
})
.then(data => console.log("Successfully registered:", data))
.catch(err => console.error("Registration error:", err));
}


createMapFoundation();
applyDefault(defaultTile);
fetchMap().then(map => {
    renderMap(map);
});





const viewport = document.getElementById("viewport");

let isDown = false;
let startX;
let startY;
let scrollLeft;
let scrollTop;
let scale = 1;

// ---------- DRAG ----------
function stopDrag() {
    isDown = false;
    viewport.classList.remove("dragging");
}

viewport.addEventListener("pointerdown", (e) => {
    isDown = true;
    viewport.classList.add("dragging");

    startX = e.clientX;
    startY = e.clientY;

    scrollLeft = viewport.scrollLeft;
    scrollTop = viewport.scrollTop;

    viewport.setPointerCapture(e.pointerId);
});

viewport.addEventListener("pointermove", (e) => {
    if (!isDown) return;

    const dx = e.clientX - startX;
    const dy = e.clientY - startY;

    viewport.scrollLeft = scrollLeft - dx;
    viewport.scrollTop = scrollTop - dy;
});

viewport.addEventListener("pointerup", stopDrag);
viewport.addEventListener("pointercancel", stopDrag);
viewport.addEventListener("lostpointercapture", stopDrag);
viewport.addEventListener("pointerleave", stopDrag);

land.addEventListener("dragstart", (e) => e.preventDefault());

// ---------- ZOOM ----------
viewport.addEventListener("wheel", (e) => {
    e.preventDefault();

    const zoomIntensity = 0.0015;
    const delta = -e.deltaY * zoomIntensity;

    const rect = viewport.getBoundingClientRect();

    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    const contentX = (viewport.scrollLeft + mouseX) / scale;
    const contentY = (viewport.scrollTop + mouseY) / scale;

    scale = Math.min(Math.max(0.5, scale + delta), 3);

    land.style.transformOrigin = "top left";
    land.style.transform = `scale(${scale})`;

    viewport.scrollLeft = contentX * scale - mouseX;
    viewport.scrollTop = contentY * scale - mouseY;
}, { passive: false });

function centerBoard() {
    const x = (land.scrollWidth * scale - viewport.clientWidth) / 2;
    const y = (land.scrollHeight * scale - viewport.clientHeight) / 2;

    viewport.scrollLeft = x;
    viewport.scrollTop = y;
}

// wait for layout
window.addEventListener("load", centerBoard);