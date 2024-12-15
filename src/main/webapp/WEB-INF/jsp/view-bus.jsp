<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <jsp:include page="head.jsp" />
  <title>Add Bus Type</title>
  <body>
    <jsp:include page="admin-header.jsp" />
    <jsp:include page="admin-sidebar.jsp" />

</head>
<body>
  <main>
    <div class="shadow-card rounded-2xl border-[1px] mt-[100px] md:mt-[100px] border-[#E5E9F1] mx-4">
          <h3 class="ml-4 px-8 py-2 text-lg font-semibold rounded-b-[15px] bg-[#EAF1FB] inline-block text-center">
            Bus
          </h3>

          <div class="m-4 space-y-4 p-4">
            <div class="mb-2 w-full flex flex-wrap gap-y-4">
              
                <div class="mb-4 w-full sm:w-1/2 lg:w-1/4 px-2">
                    <span class="view-title">Bus Name</span>
                    <p class="view-details">
                        ${bus.bus_name}
                    </p>
                </div>

                <div class="mb-4 w-full sm:w-1/2 lg:w-1/4 px-2">
                    <span class="view-title">Bus Type</span>
                    <p class="view-details">
                        ${bus.bus_type}
                    </p>
                </div>


                <div class="mb-4 w-full sm:w-1/2 lg:w-1/4 px-2">
                    <span class="view-title">Total Seats In Row</span>
                    <p class="view-details">
                        ${bus.seats_per_row}
                    </p>
                </div>

                <div class="mb-4 w-full sm:w-1/2 lg:w-1/4 px-2">
                    <span class="view-title">Number Of Rows</span>
                    <p class="view-details">
                        ${bus.total_rows}
                    </p>
                </div>
         
                <div class="mb-4 w-full sm:w-1/2 lg:w-1/4 px-2">
                    <span class="view-title">Capacity</span>
                    <p class="view-details">
                        ${bus.capacity}
                    </p>
                </div>
          </div>
    </div>

    <jsp:include page="alert.jsp" ></jsp:include>

  </main>

  <script src="/js/utils/helper.js"></script>
  <script src="/js/validations/validation.js" ></script>
  <script src="/js/utils/fetch.js" ></script>
  <script src="/js/utils/alert.js" ></script>

  </body>
  </html>