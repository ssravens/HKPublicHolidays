const tBody = document.querySelector("#t-body");

const getDataBtn = document.querySelector("#get-data");

function createTableRow(data) {
  tBody.innerHTML = ''
  data.forEach((item) => {
    const tr = document.createElement("tr");
    let td = document.createElement("td");
    td.textContent = item.uid;
    tr.appendChild(td);

    td = document.createElement("td");
    td.textContent = item.dtStart.split("T")[0];
    tr.appendChild(td);

    td = document.createElement("td");
    td.textContent = item.dtEnd.split("T")[0];
    tr.appendChild(td);

    td = document.createElement("td");
    td.textContent = item.summary;
    tr.appendChild(td);

    tBody.appendChild(tr);
  });
}

getDataBtn.addEventListener("click", () => {
  fetch(
    "https://api.data.gov.hk/v1/historical-archive/get-file?url=http%3A%2F%2Fwww.1823.gov.hk%2Fcommon%2Fical%2Fen.json&time=20220701-0912"
  )
    .then((response) => response.json())
    .then((data) => {
      console.log(data.vcalendar[0].vevent);
      fetch("http://localhost:8080/holiday/", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
        },
        body: JSON.stringify(data.vcalendar[0].vevent),
      })
        .then((response) => response.json())
        .then((data) => createTableRow(data));
    });
});

const startDate = document.querySelector("#startDate");
const endDate = document.querySelector("#endDate");
const searchBtn = document.querySelector("#searchBtn");

searchBtn.addEventListener("click", () => {
  console.log(startDate.value);
  fetch(
    `http://localhost:8080/holiday/filterByDate?startDate=${startDate.value}&endDate=${endDate.value}`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Headers":
          "Origin, X-Requested-With, Content-Type, Accept",
      },
    }
  )
    .then((response) => response.json())
    .then((data) => createTableRow(data));
});
