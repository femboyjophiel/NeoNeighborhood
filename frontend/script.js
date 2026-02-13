SERVER_URL = "http://localhost"
SERVER_PORT = 8080

MAP_LENGTHX = 16;
MAP_LENGTHY = 16;

// Gets map.json in its entirety
function fetchMap() {
return fetch (`${SERVER_URL}:${SERVER_PORT}/api/map/tiles`) 
    .then(response => {
        if (!response.ok) throw new Error(response.statusText);
        return response.json();
    })

    .then(data => data.tiles);
}


function renderMap(map) {
    map[0].forEach(tileSet => {
        console.log(tileSet)
        for (let x =0; x < tileSet.x.length; x++) {
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
            document.getElementById("land").appendChild(div)
    }
}

createMapFoundation();
fetchMap().then(map => {
    renderMap(map);
});