SERVER_URL = "http://localhost"
SERVER_PORT = 8080

MAP_LENGTHX = 16;
MAP_LENGTHY = 16;

function fetchMap() {
fetch (`${SERVER_URL}:${SERVER_PORT}/api/map/tiles`) 
    .then(response => {
        if (!response.ok) {
            throw new Error("Network is being a meanie: " + response.statusText);
        }

        return response.json()
    })

    .then(data => {
        renderMap(data);
    })
    .catch(error => {
        console.error("You did something wrong: ", error)
    })
}

function renderMap(data) {
    for (const tile in data) {

    }
}

function createMapFoundation() {
    for (let x = 0; x < MAP_LENGTHX; x++) {
        for (let y = 0; y < MAP_LENGTHY; y++) {
            let div = document.createElement("div");
            div.classList.add(x, y); //Add x and y seperately so rows/columns can be altered if need.
            div.id = x+y; //This will be used most often to target tiles specifically
            document.getElementById("land").appendChild(div)
        }
    }
}

createMapFoundation();