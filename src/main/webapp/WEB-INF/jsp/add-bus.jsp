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

          <div class="m-4 space-y-4">
            <div class="mb-2 w-full flex flex-wrap gap-y-4">
              
            <div class="mb-4 w-full sm:w-1/2 lg:w-1/4 px-2">   
              <div class="lms-input-container">
                  <input id="bus" class="lms-input" type="text" placeholder="" value="">
                  <label for="bus" class="lms-placeholder">Enter Bus Name<span class="text-danger">*</span></label>
              </div>
            </div>

            <div class="mb-4 w-full sm:w-1/2 lg:w-1/4 px-2">
              <div class="lms-input-container">
                <select id="bus-type" class="lms-dropdropdown">
                    <option value="" disabled selected hidden>Select Bus Type</option>
                     <c:forEach var="types" items="${bus_types}">
                        <option value="${types.id}">
                            ${types.bus_type}
                        </option>
                    </c:forEach>
                </select>
                <div class="lms-cut-dropdown"></div>
                <label for="parent-entity" class="lms-placeholder-dropdown">Bus
                    Types<span>*</span></label>
              </div>
            </div>

            <div class="mb-4 w-full sm:w-1/2 lg:w-1/4 px-2">
                <div class="lms-input-container">
                    <input id="seats-in-row" class="lms-input" type="number" placeholder="" value="">
                    <label for="seats-in-row" class="lms-placeholder">Total Seats In Row<span class="text-danger">*</span></label>
                </div>
           </div>

           <div class="mb-4 w-full sm:w-1/2 lg:w-1/4 px-2">
            <p class="text-gray-500">Is Last Row Combined?<span class="text-red-500">*</span></p>
            <div>
              <label for="last-row-yes" class=""> 
                  <input id="last-row-yes" class="accent-red-500" name="last-row" type="radio" value="yes">
                  Yes
              </label>&ensp;
              <label for="last-row-no" class=""> 
                  <input id="last-row-no" class="accent-red-500" name="last-row" type="radio" value="no">
                  No
              </label>
          </div>
          </div>
            

           <div class="mb-4 w-full sm:w-1/2 lg:w-1/4 px-2">
                <div class="lms-input-container">
                    <input id="seats-row-column" class="lms-input" type="number" placeholder="" value="">
                    <label for="seats-row-column" class="lms-placeholder">Number Of Rows<span class="text-danger">*</span></label>
                </div>
            </div>    
         
            <div class="mb-4 w-full sm:w-1/2 lg:w-1/4 px-2">
                <div class="lms-input-container">
                    <input id="capacity" class="lms-input" type="number" placeholder="" value="">
                    <label for="capacity" class="lms-placeholder">Seats Capacity<span class="text-danger">*</span></label>
                </div>
            </div>
          </div>
          <button class="btn font-semibold my-2" id="submit-bus" >Add +</button>
    </div>

    <jsp:include page="alert.jsp" ></jsp:include>

  </main>

  <script src="/js/utils/helper.js"></script>
  <script src="/js/validations/validation.js" ></script>
  <script src="/js/utils/fetch.js" ></script>
  <script src="/js/utils/alert.js" ></script>

  <script>


    document.getElementById('submit-bus').addEventListener('click',async () => {
      const bus_name = document.getElementById("bus").value.trim();
      const bus_type_id = document.getElementById('bus-type').value;
      const seats_per_row = document.getElementById("seats-in-row").value;
      const total_rows = document.getElementById("seats-row-column").value;
      const capacity = document.getElementById("capacity").value;
      const is_last_row = document.querySelector('input[name="last-row"]:checked').value;
      const last_row = is_last_row === 'yes' ? true : false;
      console.log("last row ",last_row);

      const busRequired = isRequired(bus_name);
      const busTypeRequired = isRequired(bus_type_id);
      const seatsRowRequired = isRequired(seats_per_row);
      const seatsRowColumnRequired = isRequired(total_rows);
      const capacityRequired = isRequired(capacity);

      if(!busRequired) {
        showAlert({alert:"error",title: "Bus Is Required" });
        return;
      }
     
      if(!busTypeRequired) { 
        showAlert({alert:"error",title: "Bus Type Is Required" });
        return;
      }

      if(!seatsRowRequired) {
        showAlert({alert:"error",title: "Seats In Row Is Required" });
        return;
      }

      if(!seatsRowColumnRequired) {
        showAlert({alert:"error",title: "Seats Row Column Is Required" });
        return;
      }

      if(!capacityRequired) {
        showAlert({alert:"error",title: "Capacity Is Required" });
        return;
      }

       const url = "/submit-bus";
       const method = 'POST';
       const obj = {bus_name,bus_type_id,seats_per_row,total_rows,capacity,last_row};
       const {error,data} = await fetchApi(url,method,obj);

       if(error) {
        showAlert({alert:"error",title: error.message});
        return;
       }
       
       if(data) {
         showAlert({alert:"success",title: data.message});
         location.href="/bus";
       }
   
      });

      document.getElementById("seats-row-column").addEventListener("input",() => {
         const seats_in_row = document.getElementById("seats-in-row").value;
         const seats_row_column = document.getElementById("seats-row-column").value;
         const is_last_row = document.querySelector('input[name="last-row"]:checked').value;
         const last_row = is_last_row === 'yes' ? true : false;
         console.log("last row ",last_row);

 
         const seatsRequiredVal = isRequired(seats_in_row);
         const seatsNumberVal = isRequired(seats_in_row);

         if(!seatsRequiredVal) {
          showAlert({alert:"error",title: "Seats In Row Is Required" });
          return;
         } 

         if(!seatsNumberVal) {
          showAlert({alert:"error",title: "Seat Rows Is Required"});
          return;
         } 

         const dropdown = document.getElementById('bus-type');
         const dropdownValue = dropdown.options[dropdown.selectedIndex].text.toUpperCase();
         let capacity;
        
         if(!last_row) {
          capacity = (seats_in_row * seats_row_column) - seats_in_row;
         }else{
          capacity = ((seats_in_row * seats_row_column) + 1) - seats_in_row;
         }
        
         document.getElementById("capacity").value = capacity;
      });
  </script>

</body>
</html>