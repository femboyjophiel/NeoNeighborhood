SERVER_URL = "https://neoneighborhood.jophiel.org";

const land = document.getElementById("land");


// prevent native drag behavior
land.addEventListener("dragstart", e => e.preventDefault());

function register() {
    let rusername = document.getElementById("register-username").value;
    let rpassword = document.getElementById("register-password").value;

fetch(`${SERVER_URL}/api/user/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username: rusername, password: rpassword })
})
.then(response => {
    if (!response.ok) throw new Error(response.statusText);
    return response.json();
})
.then(data => {
    console.log("Successfully registered:", data)
    login(rusername, rpassword);
})
.catch(err => console.error("Registration error:", err));
}

function login(username, password) {
    let lusername; 
    let lpassword;

    if (username && password) {
        lusername = username;
        lpassword = password;
    } else {
        lusername = document.getElementById("login-username").value;
        lpassword = document.getElementById("login-password").value;
    }

fetch(`${SERVER_URL}/api/user/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username: lusername, password: lpassword })
})
.then(response => {
    if (!response.ok) throw new Error(response.statusText);
    return response.json();
})
.then(data => console.log("Successfully logged in:", data))
.catch(err => console.error("Login error:", err));
}


const modal = document.getElementById("welcomeModal");
const closeBtn = document.getElementById("closeModal");

closeBtn.addEventListener("click", () => {
  modal.style.display = "none";
});

async function fetchPlotById(id) {
  try {
    const url = `${SERVER_URL}/api/plot/plotdata?id=${id}`;
    const response = await fetch(url);

    if (!response.ok) {
      throw new Error(`Error fetching plot: ${response.status}`);
    }

    const data = await response.json();
    console.log("Plot data:", data);
    return data;

  } catch (err) {
    console.error(err);
    return null;
  }
}

async function openUserModal(id) {
  try {
    const plot = await fetchPlotById(id);

    // Default "empty plot" user
    const defaultUser = {
      displayName: 'Knock Knock',
      about: 'Who\'s there? <br> No one! But in the future, it could be you! :)',
      portrait: null,
      website: null
    };

    // Use the plot's user if available, otherwise default
    let user = defaultUser;

    if (plot && plot.userInfo && Object.keys(plot.userInfo).length > 0) {
      const userId = Object.keys(plot.userInfo)[0];
      user = plot.userInfo[userId];
    }

    const overlay = document.createElement("div");
    overlay.className = "user-modal-overlay";

    const modal = document.createElement("div");
    modal.className = "user-modal";

    modal.innerHTML = `
      ${user.portrait ? `<img src="${user.portrait}" alt="${user.displayName}">` : ''}
      <h2>${user.displayName}</h2>
      <p>${user.about}</p>
      ${user.website ? `<p>Website: <a href="https://${user.website}" target="_blank">${user.website}</a></p>` : ''}
      <button class="user-close-btn">Close</button>
    `;

    overlay.appendChild(modal);
    document.body.appendChild(overlay);

    // Close button
    modal.querySelector(".user-close-btn").addEventListener("click", () => overlay.remove());

    // Click outside modal closes it
    overlay.addEventListener("click", (e) => {
      if (e.target === overlay) overlay.remove();
    });

  } catch (err) {
    console.error("Failed to open user modal:", err);
  }
}