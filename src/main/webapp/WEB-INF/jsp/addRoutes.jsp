<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <jsp:include page="head.jsp" />
  <title>View Routes</title>
  <body>
    <jsp:include page="admin-header.jsp" />
    <jsp:include page="admin-sidebar.jsp" />

    <main>
      <div class="mt-[10rem] md:mt-[10rem]">
        <div class="flex justify-end pr-4">
          <button class="btn flex items-center gap-2" id="add-route">
            <svg
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <g clip-path="url(#clip0_252_437)">
                <path
                  d="M12 0C9.62663 0 7.30655 0.703788 5.33316 2.02236C3.35977 3.34094 1.8217 5.21509 0.913451 7.4078C0.00519943 9.60051 -0.232441 12.0133 0.230582 14.3411C0.693605 16.6689 1.83649 18.8071 3.51472 20.4853C5.19295 22.1635 7.33115 23.3064 9.65892 23.7694C11.9867 24.2324 14.3995 23.9948 16.5922 23.0866C18.7849 22.1783 20.6591 20.6402 21.9776 18.6668C23.2962 16.6935 24 14.3734 24 12C23.9966 8.81846 22.7312 5.76821 20.4815 3.51852C18.2318 1.26883 15.1815 0.00344108 12 0ZM16 13H13V16C13 16.2652 12.8946 16.5196 12.7071 16.7071C12.5196 16.8946 12.2652 17 12 17C11.7348 17 11.4804 16.8946 11.2929 16.7071C11.1054 16.5196 11 16.2652 11 16V13H8.00001C7.73479 13 7.48043 12.8946 7.2929 12.7071C7.10536 12.5196 7.00001 12.2652 7.00001 12C7.00001 11.7348 7.10536 11.4804 7.2929 11.2929C7.48043 11.1054 7.73479 11 8.00001 11H11V8C11 7.73478 11.1054 7.48043 11.2929 7.29289C11.4804 7.10536 11.7348 7 12 7C12.2652 7 12.5196 7.10536 12.7071 7.29289C12.8946 7.48043 13 7.73478 13 8V11H16C16.2652 11 16.5196 11.1054 16.7071 11.2929C16.8946 11.4804 17 11.7348 17 12C17 12.2652 16.8946 12.5196 16.7071 12.7071C16.5196 12.8946 16.2652 13 16 13Z"
                  fill="white"
                />
              </g>
              <defs>
                <clipPath id="clip0_252_437">
                  <rect width="24" height="24" fill="#D2232A" />
                </clipPath>
              </defs>
            </svg>
            Add Routes
          </button>
        </div>

        <div class="frame-container mx-4 my-8">
          <div class="frame-title">
            <span>Routes</span>
          </div>
          <div class="frame-content">
            <div class="mx-auto bg-gray-50 rounded-lg shadow-md">
              <div
                class="flex justify-between items-center px-4 py-3 bg-blue-50 rounded-t-lg"
              >
                <h2 class="text-lg font-semibold text-gray-700">
                  Routes Table
                </h2>
              </div>

              <div class="p-4 bg-white">
                <jsp:include page="search.jsp" />
              </div>

              <div class="responsive-table overflow-x-auto">
                <table class="min-w-full bg-white rounded-b-lg shadow">
                  <thead
                    id="route-headers"
                    class="text-left text-gray-500 *:p-4 text-sm font-bold text-center"
                  >
                    <tr>
                      <th>S.No</th>
                      <th>Routes</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody
                    id="pagination-data"
                    class="p-4 text-md text-gray-700 text-center"
                  ></tbody>
                </table>
              </div>
              <div class="p-4 bg-white">
                <jsp:include page="pagination.jsp" />
              </div>
            </div>
          </div>
        </div>
      </div>

      <jsp:include page="alert.jsp"></jsp:include>
      <jsp:include page="modals/update-modal.jsp"></jsp:include>
      <jsp:include page="modals/delete-modal.jsp"></jsp:include>
    </main>
  </body>
  <script src="/js/utils/helper.js"></script>
  <script src="/js/utils/fetch.js"></script>
  <script src="/js/utils/pagination.js"></script>
  <script src="/js/utils/alert.js"></script>
  <script src="/js/validations/validation.js"></script>

  <script>
    function renderTable(data) {
      let tbodyElement = document.getElementById("pagination-data");
      tbodyElement.innerHTML = "";

      if (
        data &&
        data.content &&
        Array.isArray(data.content) &&
        data.content.length > 0
      ) {
        data.content.forEach((item, index) => {

          let row = document.createElement("tr");
          row.setAttribute("data-route-id", item.id);
          row.classList.add("hover:bg-blue-50");

          let cell1 = document.createElement("td");
          cell1.textContent = index + 1;
          cell1.classList.add("font-bold");
          row.appendChild(cell1);

          let cell2 = document.createElement("td");
          cell2.textContent = item.route;
          row.appendChild(cell2);

          let cell3 = document.createElement("td");
          cell3.classList.add("relative");
          cell3.classList.add("font-bold");
          cell3.innerHTML = `<button onclick="toggleDropdown(event)"
                                              class="focus:outline-none text-gray-600 px-4"><i
                                              class="fa-solid fa-ellipsis-vertical"></i></button>

                            <div id="dropdown-0"
                                class="dropdown text-sm hidden absolute bottom-0 right-0 mt-2 w-48 bg-white rounded-md shadow-lg z-10">
                                <a href="#"
                                    class="block px-4 py-2 text-gray-700 hover:bg-gray-100 flex items-center update-btn">
                                    <i class="text-red-500 fa-solid fa-pen-to-square"></i> <span
                                        class="ml-2">Update</span>
                                </a>
                                <a href="#"
                                    class="block px-4 py-2 text-gray-700 hover:bg-gray-100 flex items-center delete-btn">
                                    <i class="text-red-500 fa-solid fa-trash-can"></i><span
                                        class="ml-2">Delete</span>
                                </a>
                            </div>`;
          row.appendChild(cell3);

          tbodyElement.appendChild(row);
        });
      } else {
        let row = document.createElement("tr");
        let cell = document.createElement("td");
        cell.setAttribute("colspan", "3");
        cell.textContent = "No Data Found !";
        row.appendChild(cell);
        tbodyElement.appendChild(row);
      }
    }

    document.addEventListener("DOMContentLoaded", async () => {
      const url = "/routes-pagination";
      const page = 0;
      const size = 5;
      const search = null;
      let data = await addPagination(url, page, size, search);
      renderTable(data);
    });

    //Function TO Redirect To Add Page
    document.getElementById("add-route").addEventListener("click", () => {
      location.href = "/add-route";
    });

    document.addEventListener("click", async (event) => {
      if (event.target.closest(".delete-btn")) {
        const tableRow = event.target.closest("tr");
        const id = tableRow.getAttribute("data-route-id");

        const htmlContent = ` <h3 class="text-base font-semibold text-gray-900" id="modal-title">Delete Route</h3>
                            <div class="mt-2">
                              <p class="text-sm text-gray-500">Are you sure you want to delete route ?</p>
                            </div>`;

        document.getElementById("modal-content-delete").innerHTML = htmlContent;
        document
          .getElementById("delete-submit-btn")
          .setAttribute("data-route-id", id);
        document.querySelector(".delete-modal").classList.remove("hidden");
      }

      if (event.target.closest(".update-btn")) {
        const tableRow = event.target.closest("tr");
        let id = tableRow.getAttribute("data-route-id");

        const url = "/fetch-route/" + id;
        const method = "GET";

        const { error, data } = await fetchApi(url, method, null);

        if (error) {
          showAlert({ alert: "error", title: error.message });
        }

        if (data) {
          document.querySelector(".update-modal").classList.remove("hidden");
          let htmlContent =
            '<div class="w-full py-4">' + '<div class="lms-input-container">';

          htmlContent +=
            '<input id="route-update" class="lms-input" type="text" placeholder="" value="' +
            data.route +
            '">' +
            '<label for="route-update" class="lms-placeholder">Enter Route<span class="text-danger">*</span></label>' +
            "</div>" +
            "</div>";

          document.querySelector(".update-modal-title").innerText = "Route";  
          document.getElementById("modal-content-update").innerHTML =
            htmlContent;
          document
            .getElementById("update-submit-btn")
            .setAttribute("data-route-id", data.id);
        }
      }
    });

    //Function To Delete Route
    document
      .getElementById("delete-submit-btn")
      .addEventListener("click", async (event) => {
        const id = event.target.getAttribute("data-route-id");

        const url = "/delete-route/" + id;
        const method = "GET";
        console.log("data id ", id, url);

        const { error, data } = await fetchApi(url, method, null);
        console.log("error >> ", error);

        if (error) {
          showAlert({ alert: "error", title: error.message });
        }

        if (data) {
          console.log("data ", JSON.stringify(data));
          showAlert({ alert: "error", title: data.message });
          location.reload();
        }
      });

    //Function TO Update Route
    document
      .getElementById("update-submit-btn")
      .addEventListener("click", async (event) => {
        const id = event.target.getAttribute("data-route-id");
        const route = document.getElementById("route-update").value.trim();
        console.log("route edit ", id, route);

        const checkRequired = isRequired(route);

        if (!checkRequired) {
          showAlert({ alert: "error", title: "Route Name Is Required" });
          return;
        }

        const url = "/update-route";
        const method = "POST";
        const obj = { id, route };

        const { error, data } = await fetchApi(url, method, obj);

        if (error) {
          showAlert({ alert: "error", title: error.message });
        }

        if (data) {
          console.log("update data ", JSON.stringify(data));
          showAlert({ alert: "success", title: data.message });
          document.querySelector(".update-modal").classList.add("hidden");
          const row = document.querySelector('tr[data-route-id="' + id + '"]');
          const index = parseInt(row.firstElementChild.textContent);
          row.innerHTML = "";

          let cell1 = document.createElement("td");
          cell1.textContent = index;
          cell1.classList.add("font-bold");
          row.appendChild(cell1);

          let cell2 = document.createElement("td");
          cell2.textContent = route;
          row.appendChild(cell2);

          let cell3 = document.createElement("td");
          cell3.classList.add("relative");
          cell3.classList.add("font-bold");
          cell3.innerHTML = `<button onclick="toggleDropdown(event)"
                                              class="focus:outline-none text-gray-600 px-4"><i
                                              class="fa-solid fa-ellipsis-vertical"></i></button>

                            <div id="dropdown-0"
                                class="dropdown text-sm hidden absolute bottom-0 right-0 mt-2 w-48 bg-white rounded-md shadow-lg z-10">
                                <a href="#"
                                    class="block px-4 py-2 text-gray-700 hover:bg-gray-100 flex items-center update-btn">
                                    <i class="text-red-500 fa-solid fa-pen-to-square"></i> <span
                                        class="ml-2">Update</span>
                                </a>
                                <a href="#"
                                    class="block px-4 py-2 text-gray-700 hover:bg-gray-100 flex items-center delete-btn">
                                    <i class="text-red-500 fa-solid fa-trash-can"></i><span
                                        class="ml-2">Delete</span>
                                </a>
                            </div>`;
          row.appendChild(cell3);
        }
      });
  </script>
</html>
