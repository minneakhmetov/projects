function deleteRegisterAlert() {

    let userExists = document.getElementById("userExists");
    let doNotMatch =  document.getElementById("doNotMatch");
    let doNotFillAll = document.getElementById("doNotFillAll");

    if(userExists != null)
        userExists.remove();
    if(doNotMatch != null)
        doNotMatch.remove();
    if(doNotFillAll != null)
        doNotFillAll.remove();
}