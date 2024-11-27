//Function To Check Whether Data Is Required Or Not
function isRequired(data) {
  if (data === undefined || data === null || data === "") return false;
  return true;
}

//Function To Check Email
function checkEmail(email) {
  console.log("email val:", email);

  // Check if the email is required
  const checkRequired = isRequired(email);
  if (!checkRequired) return false;

  // Check if the email contains an '@' symbol
  const atSymbolIndex = email.indexOf("@");
  if (atSymbolIndex <= 0 || atSymbolIndex === email.length - 1) {
    return false;
  }

  // Check if the email contains a '.' symbol after the '@' symbol
  const dotIndex = email.indexOf(".", atSymbolIndex);
  if (
    dotIndex === -1 ||
    dotIndex === atSymbolIndex + 1 ||
    dotIndex === email.length - 1
  ) {
    return false;
  }

  // Check if the email contains at least two characters after the '.' symbol
  if (dotIndex > -1 && email.length - dotIndex < 3) {
    return false;
  }

  return true;
}

//Function To Check Phone No.
function checkPhoneNo(phone) {
  const checkRequired = isRequired(phone);
  if (!checkRequired) return false;
  if (!isNumber(phone)) return false;
  return true;
}

//Function To Check Numbers
function isNumber(num) {
  for (let i = 0; i < num.length; i++) {
    const ascii = num.charCodeAt(i);
    if (ascii < 48 || ascii > 57) return false;
  }
  return true;
}
