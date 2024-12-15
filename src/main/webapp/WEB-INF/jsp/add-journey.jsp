<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <jsp:include page="head.jsp" />
  <title>Add Journey</title>
  <body>
    <jsp:include page="admin-header.jsp" />
    <jsp:include page="admin-sidebar.jsp" />

</head>
<body>
  <main>
    <div class="shadow-card rounded-2xl border-[1px] mt-[100px] md:mt-[100px] border-[#E5E9F1] mx-4">
        <h3 class="ml-4 px-8 py-2 text-lg font-semibold rounded-b-[15px] bg-[#EAF1FB] inline-block text-center">
          Journey
        </h3>

        <div class="m-4 space-y-4">
          <div class="mb-2 w-full flex flex-wrap gap-y-4">
            
          <div class="mb-4 w-full sm:w-1/2 lg:w-1/2 px-2">
            <div class="lms-input-container">
              <select id="bus" class="lms-dropdropdown">
                  <option value="" disabled selected hidden>Select Bus</option>
                   <c:forEach var="busObj" items="${bus}">
                      <option value="${busObj.id}">
                          ${busObj.bus_name}
                     </option>
                  </c:forEach>
              </select>
              <div class="lms-cut-dropdown"></div>
              <label for="bus" class="lms-placeholder-dropdown">Bus<span>*</span></label>
            </div>
          </div>

          <div class="mb-4 w-full sm:w-1/2 lg:w-1/2 px-2">   
            <div class="lms-input-container">
                <input id="fare" class="lms-input" type="number" placeholder="" value="">
                <label for="fare" class="lms-placeholder">Bus Fare<span class="text-danger">*</span></label>
            </div>
          </div>

          <div class="mb-4 w-full sm:w-1/2 lg:w-1/2 px-2">
            <div class="lms-input-container">
              <select id="departure-location" class="lms-dropdropdown">
                  <option value="" disabled selected hidden>Select Departure Location</option>
                   <c:forEach var="route" items="${routes}">
                      <option value="${route.id}">
                          ${route.route}
                     </option>
                  </c:forEach>
              </select>
              <div class="lms-cut-dropdown"></div>
              <label for="departure-location" class="lms-placeholder-dropdown">Departure Location<span>*</span></label>
            </div>
          </div>

          <div class="mb-4 w-full sm:w-1/2 lg:w-1/2 px-2">
            <div class="lms-input-container">
              <select id="arrival-location" class="lms-dropdropdown">
                  <option value="" disabled selected hidden>Select Arrival Location</option>
                   <c:forEach var="route" items="${routes}">
                      <option value="${route.id}">
                          ${route.route}
                     </option>
                  </c:forEach>
              </select>
              <div class="lms-cut-dropdown"></div>
              <label for="arrival-location" class="lms-placeholder-dropdown">Arrival Location<span>*</span></label>
            </div>
          </div>

          <div class="mb-4 w-full px-2">   
            <div class="border border-slate-200 rounded-lg">
               <div class="accordion-btn px-4">
                <button id="boarding-accordion" class="w-full flex justify-between items-center py-5 text-slate-800">
                  <span class="font-semibold">Add Boarding Points</span>
                  <span id="icon-2" class="text-slate-800 transition-transform duration-300">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4">
                      <path d="M8.75 3.75a.75.75 0 0 0-1.5 0v3.5h-3.5a.75.75 0 0 0 0 1.5h3.5v3.5a.75.75 0 0 0 1.5 0v-3.5h3.5a.75.75 0 0 0 0-1.5h-3.5v-3.5Z" />
                    </svg>
                  </span>
                </button>
               </div>  
                <div id="content-2" class="max-h-0 overflow-hidden transition-all duration-300 ease-in-out px-4">
                  <div class="pb-5 text-sm text-slate-500">
                    <div class="flex justify-end">
                        <button class="btn font-semibold my-2 flex justify-end" id="accordion-content-btn" > Add +</button>
                    </div>    
                    <div class="mb-2 w-full flex flex-wrap gap-y-4 mt-4 accordion-content">  
                    
                    </div>
                </div>
                </div>
            </div>
          </div>  

          <div class="mb-4 w-full px-2">   
            <div class="border border-slate-200 rounded-lg">
               <div class="dropping-btn px-4">
                <button id="dropping-accordion" class="w-full flex justify-between items-center py-5 text-slate-800">
                  <span class="font-semibold">Add Dropping Points</span>
                  <span id="icon-3" class="text-slate-800 transition-transform duration-300">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4">
                      <path d="M8.75 3.75a.75.75 0 0 0-1.5 0v3.5h-3.5a.75.75 0 0 0 0 1.5h3.5v3.5a.75.75 0 0 0 1.5 0v-3.5h3.5a.75.75 0 0 0 0-1.5h-3.5v-3.5Z" />
                    </svg>
                  </span>
                </button>
               </div>  
                <div id="content-3" class="max-h-0 overflow-hidden transition-all duration-300 ease-in-out px-4">
                  <div class="pb-5 text-sm text-slate-500">
                    <div class="flex justify-end">
                        <button class="btn font-semibold my-2 flex justify-end" id="dropping-content-btn" > Add +</button>
                    </div>    
                    <div class="mb-2 w-full flex flex-wrap gap-y-4 mt-4 dropping-content">  
                    
                    </div>
                </div>
                </div>
            </div>
          </div> 

          <div class="mb-4 w-1/1 px-2"> 
            <div class="lms-input-container">
              <input id="journey-date" class="lms-input" type="datetime-local" placeholder="" value="">
              <label for="journey-date" class="lms-placeholder">Journey Date<span class="text-danger">*</span></label>
            </div>
          </div>


        </div>
        <button class="btn font-semibold my-2" id="submit-journey" >Add +</button>
  </div>

  <jsp:include page="alert.jsp" ></jsp:include>
  </main>

  <script src="/js/utils/helper.js"></script>
  <script src="/js/validations/validation.js" ></script>
  <script src="/js/utils/fetch.js" ></script>
  <script src="/js/utils/alert.js" ></script>

  <script>
    document.getElementById("boarding-accordion").addEventListener("click",() => {
      toggleAccordion();
    });

    
    document.getElementById("accordion-content-btn").addEventListener("click",() => {
      accordionBoardingContent();
      const content = document.getElementById('content-2');
      content.style.maxHeight = content.scrollHeight + 'px';
    });

    function accordionBoardingContent() {

      const index = boardingPointsArr.length;
      boardingPointsArr.push({ id: index + 1 });

      let accordionContent = '<div class="accordion-row mb-4 w-full flex flex-wrap gap-y-4" data-index=' + index + '>';
       
        accordionContent +=  '<div class="mb-4 w-full sm:w-1/3 lg:w-1/3 px-2"><div class="lms-input-container">' +
              '<input class="lms-input" type="text" placeholder="" value="" oninput = updateBoardingPoints('+index+',"boarding_point",event.target.value)>'
        accordionContent+= `<label class="lms-placeholder">Boarding Point<span class="text-danger">*</span></label>
            </div>
          </div>
          <div class="mb-4 w-full sm:w-1/3 lg:w-1/3 px-2">   
            <div class="lms-input-container">`
       accordionContent+= '<input class="lms-input" type="time" placeholder="" value="" onchange = updateBoardingPoints('+index+',"boarding_time",event.target.value)>' +
            '<label class="lms-placeholder">Boarding Time<span class="text-danger">*</span></label>' +
          '</div></div>' +
          '<div class="mb-4 w-full sm:w-1/3 lg:w-1/3 px-2 flex justify-end">' +
            '<button class="btn font-semibold my-2" id="remove-content-btn">Remove -</button>' +
          '</div></div>';

     document.querySelector(".accordion-content").insertAdjacentHTML("beforeend", accordionContent);
}


    document.addEventListener("click", (event) => {
      if (event.target.closest("#remove-content-btn")) {
        const row = event.target.closest(".accordion-row");
        if (row) {
          row.remove();
        } 
      }
    });

    document.getElementById("dropping-accordion").addEventListener("click",() => {
      toggleDroppingAccordion();
    });

    
    document.getElementById("dropping-content-btn").addEventListener("click",() => {
      accordionDroppingContent();
      const content = document.getElementById('content-3');
      content.style.maxHeight = content.scrollHeight + 'px';
    });

    function accordionDroppingContent() {
      const index = droppingPointsArr.length;
      droppingPointsArr.push({ id: index + 1 });

      let accordionContent = '<div class="dropping-row mb-4 w-full flex flex-wrap gap-y-4" data-index=' + index + '>';
       
       accordionContent +=  '<div class="mb-4 w-full sm:w-1/3 lg:w-1/3 px-2"><div class="lms-input-container">' +
             '<input class="lms-input" type="text" placeholder="" value="" oninput = updateDroppingPoints('+index+',"dropping_point",event.target.value)>'
       accordionContent+= `<label class="lms-placeholder">Dropping Point<span class="text-danger">*</span></label>
           </div>
         </div>
         <div class="mb-4 w-full sm:w-1/3 lg:w-1/3 px-2">   
           <div class="lms-input-container">`
      accordionContent+= '<input class="lms-input" type="time" placeholder="" value="" onchange = updateDroppingPoints('+index+',"dropping_time",event.target.value)>' +
           '<label class="lms-placeholder">Dropping Time<span class="text-danger">*</span></label>' +
         '</div></div>' +
         '<div class="mb-4 w-full sm:w-1/3 lg:w-1/3 px-2 flex justify-end">' +
           '<button class="btn font-semibold my-2" id="remove-dropping-btn">Remove -</button>' +
         '</div></div>';

     document.querySelector(".dropping-content").insertAdjacentHTML("beforeend", accordionContent);
}


    document.addEventListener("click", (event) => {
      if (event.target.closest("#remove-dropping-btn")) {
        const row = event.target.closest(".dropping-row");
        if (row) {
          row.remove();
        } 
      }
    });

    let boardingPointsArr = [];

    function updateBoardingPoints(index, field, value) {
      boardingPointsArr[index] = {
        ...boardingPointsArr[index],
        [field]: value,
      };
      console.log(boardingPointsArr); 
    }

    let droppingPointsArr = [];

    function updateDroppingPoints(index, field, value) {
      droppingPointsArr[index] = {
        ...droppingPointsArr[index],
        [field]: value,
      };
    }

    document.getElementById("submit-journey").addEventListener("click", async () => {
      const busId = document.getElementById("bus").value;
      const fare = document.getElementById("fare").value;
      const departureId = document.getElementById("departure-location").value;
      const arrivalId = document.getElementById("arrival-location").value;
      const boardingPoints = boardingPointsArr.map((data) => {boading_point, boarding_time});
      const droppingPoints = droppingPointsArr.map((data) => {dropping_point, dropping_time});
      const journey_date = document.getElementById("journey-date").value;
      
      const obj = {busId, fare, departureId, arrivalId, boardingPoints, droppingPoints, journey_date};
      console.log("journey details ",JSON.stringify(obj));

      const busVal = isRequired(busId);
      const fareVal = isRequired(fare);
      const departureVal = isRequired(departureId);
      const arrivalVal = isRequired(arrivalId);
      const journeyVal = isRequired(journey_date);

      if(!busVal) {
        showAlert({
          title: "error",
          message: "Please select a bus",
        });
        return;
      }

      if(!fareVal) {
        showAlert({
          title: "error",
          message: "Please enter a fare",
        });
        return;
      }

      if(!departureVal) {
        showAlert({
          title: "error",
          message: "Please enter departure location",
        });
        return;
      }

      if(!arrivalVal) {
        showAlert({
          title: "error",
          message: "Please enter arrival location",
        });
        return;
      }

      if(boardingPoints.length === 0) {
        showAlert({
          title : "error",
          message : "Please add boarding points",
        });
        return;
      }

      if(droppingPoints.length === 0) {
        showAlert({
          title : "error",
          message : "Please add dropping points",
        });
        return;
      }
     });

     if(!journeyVal) {
        showAlert({
          title: "error",
          message: "Please select journey date",
        });
        return;
    }

    const url = "/submit-journey";
    const method = "POST";
    const {error, data} = await fetchApi(url, method, obj);

    if(error) {
      showAlert({
        title: "error",
        message: error.message,
      });
      return;
    }

    if(data) {
      showAlert({
        title: "success",
        message: data.message,
      });
      location.href = "/journey";
    }


  </script>
</body>
</html>