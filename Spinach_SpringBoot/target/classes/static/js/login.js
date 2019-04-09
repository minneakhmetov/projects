function deleteAlert() {
    let alert = document.getElementById("errorAlert");
    if(alert != null){
        alert.remove();
        document.getElementById("card").style.height = "16.3 rem";

    }
}