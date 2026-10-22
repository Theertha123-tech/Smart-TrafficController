* {
    box-sizing: border-box;
}

body {
    margin: 0;
    font-family: Arial, sans-serif;
    background: #f1f5f9;
    color: #1e293b;
}

header {
    background: #0f172a;
    color: white;
    padding: 24px 6%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    gap: 15px;
}

header h1 {
    margin: 0 0 8px;
    font-size: 26px;
}

header p {
    margin: 0;
    color: #cbd5e1;
}

.status {
    background: #14532d;
    color: #bbf7d0;
    padding: 10px 14px;
    border-radius: 20px;
    font-size: 12px;
}

main {
    max-width: 1200px;
    margin: auto;
    padding: 24px;
}

.stats {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 16px;
    margin-bottom: 22px;
}

.stat-card,
.panel {
    background: white;
    padding: 22px;
    border-radius: 14px;
    box-shadow: 0 3px 12px #0f172a0d;
}

.stat-card p {
    color: #64748b;
    margin: 0 0 12px;
    font-size: 14px;
}

.stat-card h2 {
    margin: 0;
    color: #2563eb;
    font-size: 28px;
}

.panel {
    margin-bottom: 22px;
}

.panel h2 {
    margin-top: 0;
    font-size: 20px;
}

form {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 12px;
}

label {
    font-size: 14px;
    font-weight: bold;
    align-self: end;
}

input,
select {
    width: 100%;
    padding: 12px;
    border: 1px solid #cbd5e1;
    border-radius: 8px;
    background: white;
    font-size: 14px;
}

button {
    padding: 12px 18px;
    border: none;
    border-radius: 8px;
    background: #2563eb;
    color: white;
    font-size: 14px;
    font-weight: bold;
    cursor: pointer;
}

button:hover {
    filter: brightness(.92);
}

button:focus-visible,
input:focus-visible,
select:focus-visible {
    outline: 3px solid #93c5fd;
    outline-offset: 2px;
}

button:disabled {
    opacity: .6;
    cursor: wait;
}

form button {
    grid-column: 1 / -1;
}

.hint {
    font-size: 13px;
    color: #64748b;
    line-height: 1.6;
}

.lanes {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 14px;
    margin: 20px 0;
}

.lane {
    background: #e2e8f0;
    padding: 16px;
    border-radius: 12px;
    border: 2px solid transparent;
    min-width: 0;
}

.lane.active {
    border-color: #16a34a;
    background: #f0fdf4;
}

.lane h3 {
    margin-top: 0;
    font-size: 16px;
}

.density {
    display: inline-block;
    padding: 5px 9px;
    border-radius: 20px;
    font-size: 11px;
    font-weight: bold;
    background: #dcfce7;
    color: #166534;
}

.density.medium {
    background: #fef3c7;
    color: #92400e;
}

.density.high {
    background: #fee2e2;
    color: #991b1b;
}

.traffic-light {
    background: #1e293b;
    width: 48px;
    padding: 7px;
    border-radius: 20px;
    margin: 14px auto;
    display: flex;
    flex-direction: column;
    gap: 6px;
    align-items: center;
}

.bulb {
    width: 25px;
    height: 25px;
    border-radius: 50%;
    background: #475569;
}

.bulb.red.on {
    background: #ef4444;
    box-shadow: 0 0 10px #ef4444;
}

.bulb.yellow.on {
    background: #facc15;
    box-shadow: 0 0 10px #facc15;
}

.bulb.green.on {
    background: #22c55e;
    box-shadow: 0 0 10px #22c55e;
}

.vehicle-item {
    background: white;
    padding: 9px;
    border-radius: 7px;
    margin-top: 8px;
    font-size: 12px;
    overflow-wrap: anywhere;
}

.vehicle-item.emergency {
    border-left: 4px solid #dc2626;
}

.queue {
    margin-top: 15px;
    font-weight: bold;
    font-size: 13px;
}

.control-btn {
    background: #15803d;
    margin-right: 8px;
    margin-bottom: 8px;
}

.reset-btn {
    background: #64748b;
    margin-bottom: 8px;
}

#emergencyList {
    display: grid;
    gap: 8px;
}

#activityLog {
    padding-left: 22px;
}

#activityLog li {
    margin-bottom: 10px;
    font-size: 13px;
    line-height: 1.5;
}

footer {
    text-align: center;
    background: #0f172a;
    color: #cbd5e1;
    padding: 22px;
    line-height: 1.8;
    font-size: 13px;
}

@media (max-width: 800px) {
    .stats {
        grid-template-columns: repeat(2, 1fr);
    }

    .lanes {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 480px) {
    main {
        padding: 14px;
    }

    header {
        padding: 22px;
    }

    header h1 {
        font-size: 21px;
    }

    form {
        grid-template-columns: 1fr;
    }

    form button {
        grid-column: auto;
    }

    .lanes {
        grid-template-columns: 1fr;
    }
}