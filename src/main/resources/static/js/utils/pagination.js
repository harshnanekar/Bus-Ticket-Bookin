paginateUrl = "";
pageSize = 0;
async function addPagination(url, page, size, search) {
 
  paginateUrl = url;
  pageSize = size;
  const method = "GET";
  const { error, data } = await fetchApi(
    `${url}?page=${page}&size=${size}&search=${search}`,
    method,
    null
  );

  if (error) {
    showAlert({ alert: "error", title: error.message });
  }

  if (data) {
    console.table("pagination data ", data);
    updatePaginationButtons(data);
    return data;
  }
}

function updatePaginationButtons(data) {
  let paginationDiv = document.getElementById("pagination-buttons");
  paginationDiv.innerHTML = "";

  // Create and append "Previous" button
  let prevButton = document.createElement("button");
  prevButton.textContent = "Previous";
  prevButton.disabled = data.first;
  prevButton.setAttribute("id", "previous-button");
  if (!data.first) {
    prevButton.addEventListener("click", async () => {
      let paginatedata = await addPagination(
        paginateUrl,
        data.number - 1,
        pageSize,
        null
      );
      renderTable(paginatedata);
    });
  }
  paginationDiv.appendChild(prevButton);

  // Loop through pages and create page buttons
  for (let i = 0; i < data.totalPages; i++) {
    let pageButton = document.createElement("button");
    pageButton.textContent = i + 1;
    pageButton.classList.add("paginationSelectionButtons");
    if (i === data.number) {
      pageButton.classList.add("active");
    } else {
      pageButton.addEventListener("click", async () => {
        let data = await addPagination(paginateUrl, i, pageSize, null);
        renderTable(data);
      });
    }
    paginationDiv.appendChild(pageButton);
  }

  // Create and append "Next" button
  let nextButton = document.createElement("button");
  nextButton.textContent = "Next";
  nextButton.disabled = data.last;
  nextButton.setAttribute("id", "next-button");

  if (!data.last) {
    nextButton.addEventListener("click", async () => {
      let paginatedata = await addPagination(
        paginateUrl,
        data.number + 1,
        pageSize,
        null
      );
      renderTable(paginatedata);
    });
  }
  paginationDiv.appendChild(nextButton);
}

document
  .querySelector(".pagination-search")
  .addEventListener("input", async (event) => {
    const searchValue =
      event.target.value.trim() === "" ? null : event.target.value.trim();
    const page = 0;
    console.log("Search term:", searchValue, paginateUrl);

    const paginateData = await addPagination(
      paginateUrl,
      page,
      pageSize,
      searchValue
    );
    renderTable(paginateData);
  });
