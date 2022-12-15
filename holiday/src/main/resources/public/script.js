const tBody = document.querySelector("#t-body");

const getDataBtn = document.querySelector("#get-data");

const pag = document.querySelector("#pag");

let currentPage = 1;
const numberOfHoliday = 10;
let holidays = [];

function createTableRow(data) {
  tBody.innerHTML = "";
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
        fetch("http://localhost:8080/holiday/", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
          },
          body: JSON.stringify(data.vcalendar[0].vevent),
        })
            .then((response) => response.json())
            .then((data) => {
              holidays = []
              holidays.push(...data);
              createTableRow(
                  holidays.slice(currentPage - 1, currentPage + numberOfHoliday - 1)
              );
              if (holidays.length / numberOfHoliday > 1) {
                displayNav(holidays.length);
              }
            });
      });
});
const bottomNav = document.getElementById("bottom-pagination");
function displayNav(totalHolidays) {
  const totalPages = totalHolidays
      ? Math.ceil(totalHolidays / numberOfHoliday)
      : Math.ceil(holidays.length / numberOfHoliday);

  displayNavElement(bottomNav, totalPages);
}

function displayNavElement(nav, totalPages) {
  nav.innerHTML = "";
  if (currentPage !== 1) {
    nav.appendChild(createList(false, false, "<<", "first"));
    nav.appendChild(createList(false, false, "<", "prev"));
    nav.appendChild(createList(false, false, currentPage - 1, currentPage - 1));
  }
  nav.appendChild(createList(true, false, currentPage, currentPage));

  if (currentPage !== totalPages) {
    if (currentPage + 1 !== totalPages) {
      nav.appendChild(
          createList(false, false, currentPage + 1, currentPage + 1)
      );
    }
    nav.appendChild(createList(false, false, "...", "no-action"));
    nav.appendChild(createList(false, false, totalPages, totalPages));
    nav.appendChild(createList(false, false, ">", "next"));
    nav.appendChild(createList(false, false, ">>", "last"));
  }
}

function createList(isActive, isDisabled, displayName, id) {
  const pageLink = document.createElement("a");
  pageLink.classList.add("page-link");
  pageLink.innerHTML = displayName;
  pageLink.href = "#";

  const pageItem = document.createElement("li");
  pageItem.setAttribute("id", id);
  pageItem.classList.add("page-item");
  pageItem.setAttribute("title", getHelpMessage(displayName));

  if (isActive) {
    pageItem.classList.add("active");
  }
  if (isDisabled) {
    pageItem.classList.add("disabled");
  }
  pageItem.appendChild(pageLink);
  pageItem.addEventListener("click", () => handleSelectPage(id));
  return pageItem;
}

function getHelpMessage(displayName) {
  let current;
  if (isNaN(parseInt(displayName))) {
    if (displayName === "...") return "";
    if (displayName === ">") {
      current = currentPage + 1;
    } else if (displayName === ">>") {
      current = Math.ceil(holidays.length / numberOfHoliday);
    } else if (displayName === "<") {
      current = currentPage - 1;
    } else if (displayName === "<<") {
      current = 1;
    }
  }
  if (!current) {
    current = +displayName;
  }
  const start = (current - 1) * numberOfHoliday;
  const end = current * numberOfHoliday;
  return `${start} - ${end}`;
}

function handleSelectPage(id) {
  if (isNaN(parseInt(id))) {
    if (id === "no-action") return;
    if (id === "next") {
      currentPage++;
    } else if (id === "last") {
      currentPage = Math.ceil(holidays.length / numberOfHoliday);
    } else if (id === "prev") {
      currentPage--;
    } else if (id === "first") {
      currentPage = 1;
    }
  } else {
    currentPage = +id;
  }
  displayNav();
  createTableRow(
      holidays.slice(
          (currentPage - 1) * numberOfHoliday,
          currentPage * numberOfHoliday
      )
  );
}

const startDate = document.querySelector("#startDate");
const endDate = document.querySelector("#endDate");
const searchBtn = document.querySelector("#searchBtn");

searchBtn.addEventListener("click", () => {
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
      .then((data) => {
        holidays = [];
        holidays.push(...data);
        createTableRow(
            holidays.slice(currentPage - 1, currentPage + numberOfHoliday - 1)
        );
        if (holidays.length / numberOfHoliday > 1) {
          displayNav(holidays.length);
        }else {
          bottomNav.innerHTML = "";
        }
      });
});