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
            Bus Types
          </h3>

          <div class="m-4 space-y-4">
            <div class="mb-2 w-full px-2">
              <div class="lms-input-container">
                  <input id="bus-type" class="lms-input" type="text" placeholder="" value="">
                  <label for="bus-type" class="lms-placeholder">Enter Bus Type<span class="text-danger">*</span></label>
              </div>
            </div>
              <button class="btn font-semibold my-2" id="submit-bus-type" >Add +</button>
          </div>
    </div>

    <jsp:include page="alert.jsp" ></jsp:include>
    <jsp:include page="loader.jsp" ></jsp:include>

  </main>

  <script src="/js/utils/helper.js"></script>
  <script src="/js/validations/validation.js" ></script>
  <script src="/js/utils/fetch.js" ></script>
  <script src="/js/utils/alert.js" ></script>

  <script>
    document.getElementById('submit-bus-type').addEventListener('click',async () => {
      const bus_type = document.getElementById('bus-type').value.trim();
      const checkRequired = isRequired(bus_type);
      showLoader(true);
     
      if(!checkRequired) { 
        showAlert({alert:"error",title: "Bus Type Is Required" });
        return;
      }

       const url = "/submit-bus-type";
       const method = 'POST';
       const obj = {bus_type};
       const {error,data} = await fetchApi(url,method,obj);

       if(error) {
        showAlert({alert:"error",title: error.message});
       }
       
       if(data) {
         showAlert({alert:"success",title: data.message});
         location.href="/bus-types";
       }
   
      });
  </script>

</body>
</html>