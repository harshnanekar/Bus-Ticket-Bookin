
// Utility function to get a specific cookie value by name
function getCookie(name) {
  const cookieArr = document.cookie.split(";");
  for (let i = 0; i < cookieArr.length; i++) {
    let cookie = cookieArr[i].trim();

    if (cookie.startsWith(name + "=")) {
      return cookie.substring(name.length + 1);
    }
  }
  return null;
}

async function fetchApi(url, method = "GET", data = null) {
  const options = {
    method,
    headers: {
      Accept: "application/json",
    },
  };

  if (method === "POST" && data) {
    if (data instanceof FormData) {
      options.body = data;
    } else {
      options.headers["Content-Type"] = "application/json";
      options.body = JSON.stringify(data);
    }
  }

  try {
    const response = await fetch(url, options);
    console.log("response ", response);

    if (response.status == 401) {
      if (location.pathname === "/loginPage") {
        return {
          error: { status: response.status, message: "Invalid Credentails !" },
          data: null,
        };
      } else {
        location.href = "/loginPage";
        return;
      }
    }

    if (response.status == 403) {
      location.href = "/test";
    }

    let data = await response.json();

    if (response.ok) {
      return { error: null, data };
    } else {
      return {
        error: { status: response.status, message: data.message },
        data: null,
      };
    }
  } catch (error) {
    console.log('error ',error);
    return {
      error: { status: error.status, message: error.message },
      data: null,
    };
  }
}
