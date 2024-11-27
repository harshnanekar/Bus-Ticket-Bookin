<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> -->
<!-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> -->
<!DOCTYPE html>
<html lang="en">
  <jsp:include page="head.jsp" />
  <title>Login</title>
  <body>
    <div class="flex justify-center items-center h-screen bg-gray-100">
      <div class="relative w-1/3">
        <div
          class="absolute inset-0 bg-gradient-to-r from-red-300 to-red-600 shadow-lg transform -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl"
        ></div>

        <!-- Content Block -->
        <div
          class="relative flex flex-col justify-center items-center p-4 bg-white rounded-2xl"
        >
          <img
            class="w-1/4"
            src="/resource/images/bus_logo_new.png"
            alt="Bus Logo"
          />
          <h2 class="text-center font-bold text-[30px] m-4">Login</h2>
          <div class="w-full px-8">
            <input
              class="input-box my-3 shadow-slate-300"
              type="text"
              placeholder="Email-Id"
              id="username"
            />
            <input
              class="input-box my-3 shadow-slate-300"
              type="password"
              placeholder="Password"
              id="password"
            />
            <div class="w-full flex justify-end">
              <a href="dcs" class="text-red-600 hover:underline my-4"
                >Forget Password?</a
              >
            </div>
            <div class="w-full flex">
              <button class="btn" id="submitBtn">Login</button>
            </div>
          </div>
          <p class="md:text-md mt-6">
            For any support queries please contact us at:
          </p>
          <p class="md:text-md">9137208307</p>
          <i class="md:text-md mb-6">(Between 10:00 am to 6:00 pm)</i>
        </div>
      </div>
    </div>

    <jsp:include page="alert.jsp" ></jsp:include>
   
  </body>

  <script src="/js/validations/validation.js"></script>
  <script src="/js/utils/fetch.js"></script>
  <script src="/js/utils/alert.js"></script>


  <script>
    //Function To Handle Login
    document.getElementById("submitBtn").addEventListener("click", async () => {
      const username = document.getElementById("username").value;
      const password = document.getElementById("password").value;

      let checkIsNumber = isNumber(username);
      let checkPassword = isRequired(password);

      if (username === undefined || username === null || username === "") {
        showAlert({alert:"error",title :"Email-Id Is Required !"});
        return;
      }

      if (password === undefined || password === null || password === "") {
        showAlert({alert:"error",title: "Password Is Required !" });
        return;
      }

      let emailVal = checkEmail(username);

      if (emailVal && checkPassword) {
        let url = "/login";
        let method = "POST";
        const formData = new FormData();
        formData.append("username", username);
        formData.append("password", password);

        const { error, data } = await fetchApi(url, method, formData);
        console.log("json response ", JSON.stringify(data));

        if (error) {
            showAlert({alert:"error",title: error.message });
        }

        if (data && data.status == 200) {
          location.href = "/dashboard";
        }

      } else {
        showAlert({alert:"error",title: "Invalid Credentials !" });   
        return;
      }
    });
  </script>
</html>
