document.addEventListener("DOMContentLoaded", function(){
    // START OF VALIDATIONS
    let today = new Date();

    // Get the day of the week (0 = Sunday, 1 = Monday, ..., 6 = Saturday)
    let currentDay = today.getDay();
    let daysFromMonday = (currentDay === 0) ? 6 : currentDay - 1; // if Sunday, shift to previous Monday

    // Set to the Monday of the current week
    let startOfWeek = new Date(today);
    startOfWeek.setDate(today.getDate() - daysFromMonday);

    // Get end of the 4th week (Sunday of 4th week)
    let endOfFourthWeek = new Date(startOfWeek);
    endOfFourthWeek.setDate(startOfWeek.getDate() + (7 * 4) - 1); // 4 full weeks minus 1 day (so it's Sunday)

    // Format dates
    function formatDate(date) {
        return date.getFullYear() + "-" +
               String(date.getMonth() + 1).padStart(2, '0') + "-" +
               String(date.getDate()).padStart(2, '0');
    }

    const minDate = new Date();
    minDate.setDate(minDate.getDate() + 1); // Tomorrow

    // Set min and max
    document.getElementById('date-input').setAttribute('min', formatDate(minDate));
    document.getElementById('date-input').setAttribute('value', formatDate(minDate));
    document.getElementById('date-input').setAttribute('max', formatDate(endOfFourthWeek));
    
    
    
    
    // TABLE NAVIGATION
    const tables = document.querySelectorAll(".inner-table-wrapper");
    const rBtn = document.querySelectorAll(".right-btn");
    
    const lBtn = document.querySelectorAll(".left-btn");
    lBtn.forEach(function(btn){
        btn.addEventListener("click", function(e){
            
            //get the active table index, remove table-ative
            const activeTable = document.querySelector(".table-active");
            let activeIndex = parseInt(activeTable.getAttribute("data-index"));
            
            //if left most still press left, cannot
            if (activeIndex === 0){
                return;
            }
            
            //if right most and press left, re enable right button
            if(activeIndex === 3){
                rBtn.forEach(r => r.disabled = false);
            }
            
            activeTable.classList.remove("table-active");
            activeTable.classList.add("d-none");

            
            let nextIndex = activeIndex - 1;
            
            //next one is left most then disable it
            if (nextIndex === 0) {
                lBtn.forEach(l => l.disabled = true);
            }
            
            const nextTable = document.querySelector(`.inner-table-wrapper[data-index="${nextIndex}"]`);
            nextTable.classList.add("table-active");
            nextTable.classList.remove("d-none");

        });
    });
    

    rBtn.forEach(function (btn) {
        btn.addEventListener("click", function () {
            const activeTable = document.querySelector(".table-active");
            let activeIndex = parseInt(activeTable.getAttribute("data-index"));

            // If already at the last table, do nothing
            if (activeIndex === 3) {
                return;
            }

            // Enable left buttons if coming from index 0
            if (activeIndex === 0) {
                lBtn.forEach(l => l.disabled = false);
            }
            
            activeTable.classList.remove("table-active");
            activeTable.classList.add("d-none");

            let nextIndex = activeIndex + 1;

            // Disable right buttons if next is the last index
            if (nextIndex === 3) {
                rBtn.forEach(r => r.disabled = true);
            }

            const nextTable = document.querySelector(`.inner-table-wrapper[data-index="${nextIndex}"]`);
            nextTable.classList.add("table-active");
            nextTable.classList.remove("d-none");

        });
    });
    

    
    
    // DELETE TIME SLOT
    const removeBtns = document.querySelectorAll(".remove-btn");
    removeBtns.forEach(function(btn){
        btn.addEventListener("click",function(e){
            const slotid = btn.getAttribute("data-slotid");
            const firstPath = location.pathname.split('/')[1];
            
            $.ajax({
                type: "POST",
                data: {
                    slotid: slotid
                },
                url: "/" + firstPath + "/DeleteTimeSlotServlet",
                success: function (response) {
                    if (response.redirectUrl) {
                        window.location.href = response.redirectUrl;
                    }
                }
            });
        });
    });
    


});

function validateTimeSlot() {
    const startTimeStr = document.querySelector('select[name="starttime"]').value;
    const duration = parseInt(document.querySelector('select[name="hour"]').value);

    // Parse start hour from value (e.g., "13:00" → 13)
    const startHour = parseInt(startTimeStr.split(":")[0]);
    const endHour = startHour + duration;

    // Constraint 1: Must end before or at 19:00 (7pm)
    if (endHour > 19) {
        alert("Session must end by 7:00 PM.");
        return false;
    }

    // Constraint 2: Cannot overlap 12:00–13:00 (break time)
    // Overlap if session starts before 13 and ends after 12
    console.log(startHour);
    console.log(endHour);
    if (startHour < 13 && endHour > 12) {
        alert("Session cannot overlap therapist's break time (12:00 PM to 1:00 PM).");
        return false;
    }

    return true; // All checks passed
}
    

