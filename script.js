const lanesContainer = document.getElementById("lanes");
const emergencyList = document.getElementById("emergencyList");
const activityLog = document.getElementById("activityLog");

let lanes = [[], [], [], []];
let activeLane = -1;
let passedCount = 0;
let sequence = 0;

const displayNames = {
    normal: "Normal Vehicle",
    ambulance: "Ambulance",
    fire: "Fire Engine",
    police: "Police Vehicle"
};

const priority = {
    ambulance: 1,
    fire: 2,
    police: 3,
    normal: 4
};

function logActivity(message) {
    const item = document.createElement("li");
    item.textContent =
        `${new Date().toLocaleTimeString()} — ${message}`;
    activityLog.prepend(item);

    while (activityLog.children.length > 8) {
        activityLog.lastElementChild.remove();
    }
}

function getEmergencies() {
    return lanes.flat()
        .filter(v => v.type !== "normal")
        .sort((a, b) =>
            priority[a.type] - priority[b.type] ||
            a.sequence - b.sequence
        );
}

function getDensity(count) {
    if (count <= 3) return "Low";
    if (count <= 6) return "Medium";
    return "High";
}

function renderTraffic() {
    const totalWaiting = lanes.reduce(
        (sum, lane) => sum + lane.length, 0
    );

    document.getElementById("totalVehicles").textContent =
        totalWaiting;
    document.getElementById("emergencyCount").textContent =
        getEmergencies().length;
    document.getElementById("activeLane").textContent =
        activeLane === -1 ? "None" : `Lane ${activeLane + 1}`;
    document.getElementById("passedCount").textContent =
        passedCount;

    lanesContainer.replaceChildren();

    const directions = ["North", "South", "East", "West"];

    lanes.forEach((vehicles, index) => {
        const card = document.createElement("div");
        card.className =
            "lane" + (activeLane === index ? " active" : "");

        const title = document.createElement("h3");
        title.textContent =
            `Lane ${index + 1} — ${directions[index]}`;

        const density = document.createElement("span");
        density.className =
            "density " + getDensity(vehicles.length).toLowerCase();
        density.textContent =
            getDensity(vehicles.length) + " TRAFFIC";

        const light = document.createElement("div");
        light.className = "traffic-light";

        ["red", "yellow", "green"].forEach(color => {
            const bulb = document.createElement("div");
            bulb.className = "bulb " + color;

            if (activeLane === index && color === "green") {
                bulb.classList.add("on");
            } else if (activeLane !== index && color === "red") {
                bulb.classList.add("on");
            }

            light.appendChild(bulb);
        });

        const queueTitle = document.createElement("p");
        queueTitle.className = "queue";
        queueTitle.textContent = `Waiting: ${vehicles.length}`;

        card.append(title, density, light, queueTitle);

        if (vehicles.length === 0) {
            const empty = document.createElement("div");
            empty.className = "vehicle-item";
            empty.textContent = "No vehicles waiting";
            card.appendChild(empty);
        }

        vehicles.forEach(vehicle => {
            const item = document.createElement("div");
            item.className = "vehicle-item";
            item.textContent =
                `${displayNames[vehicle.type]} — ${vehicle.number}`;
            card.appendChild(item);
        });

        lanesContainer.appendChild(card);
    });

    emergencyList.replaceChildren();
    const emergencies = getEmergencies();

    if (emergencies.length === 0) {
        emergencyList.textContent =
            "No emergency vehicles waiting.";
    } else {
        emergencies.forEach((vehicle, index) => {
            const item = document.createElement("div");
            item.className = "vehicle-item emergency";
            item.textContent =
                `${index + 1}. ${displayNames[vehicle.type]} — ` +
                `${vehicle.number} (Lane ${vehicle.lane + 1})`;
            emergencyList.appendChild(item);
        });
    }
}

document.getElementById("vehicleForm").addEventListener(
    "submit", event => {
        event.preventDefault();

        const number =
            document.getElementById("vehicleNumber").value.trim();
        const lane =
            Number(document.getElementById("lane").value);
        const type =
            document.getElementById("vehicleType").value;

        if (!number) {
            alert("Please enter a vehicle number.");
            return;
        }

        if (!Number.isInteger(lane) || lane < 0 || lane > 3) {
            alert("Please select a valid lane.");
            return;
        }

        if (!Object.hasOwn(priority, type)) {
            alert("Please select a valid vehicle type.");
            return;
        }

        lanes[lane].push({
            number,
            lane,
            type,
            sequence: sequence++
        });

        logActivity(
            `${displayNames[type]} ${number} added to Lane ${lane + 1}.`
        );

        event.target.reset();
        renderTraffic();
    }
);

document.getElementById("controlBtn").addEventListener(
    "click", () => {
        const emergencies = getEmergencies();
        let chosenLane = -1;

        if (emergencies.length > 0) {
            chosenLane = emergencies[0].lane;
        } else {
            const nonEmpty = lanes
                .map((vehicles, index) => ({ vehicles, index }))
                .filter(item => item.vehicles.length > 0)
                .sort((a, b) =>
                    b.vehicles.length - a.vehicles.length
                );

            if (nonEmpty.length > 0) {
                chosenLane = nonEmpty[0].index;
            }
        }

        if (chosenLane === -1) {
            activeLane = -1;
            renderTraffic();
            logActivity("No vehicles waiting. All lanes are clear.");
            return;
        }

        activeLane = chosenLane;
        const vehicle = lanes[chosenLane].shift();
        passedCount++;

        logActivity(
            `${displayNames[vehicle.type]} ${vehicle.number} passed ` +
            `from Lane ${chosenLane + 1}.`
        );

        renderTraffic();
    }
);

document.getElementById("resetBtn").addEventListener(
    "click", () => {
        if (!confirm("Clear all vehicles and reset the simulation?")) {
            return;
        }

        lanes = [[], [], [], []];
        activeLane = -1;
        passedCount = 0;
        sequence = 0;
        activityLog.replaceChildren();

        renderTraffic();
        logActivity("Traffic simulation reset.");
    }
);

renderTraffic();
logActivity("Traffic simulation ready.");
