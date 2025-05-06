// clearPlot.js - Handles the clear plot button click
document.addEventListener('DOMContentLoaded', function() {
    const clearPlotBtn = document.getElementById('clearPlotBtn');
    
    if (clearPlotBtn) {
        clearPlotBtn.addEventListener('click', function() {
            // Find the visible plot container
            const visiblePlot = document.querySelector('.plot-container:not([style*="display: none"])');
            
            if (!visiblePlot) {
                alert('No active plot found. Please try again.');
                return;
            }
            
            // Get the land ID from the visible plot's data attribute
            const landId = visiblePlot.getAttribute('data-land-id');
            
            if (!landId) {
                alert('Land ID not found for the active plot. Please try again.');
                return;
            }
            
            // Create a form and submit it to the servlet
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '/MizukiForest/ClearPlotServlet';
            
            // Add the landId as a hidden input
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'landId';
            input.value = landId;
            
            form.appendChild(input);
            document.body.appendChild(form);
            
            // Ask for confirmation before submitting
            if (confirm('Are you sure you want to clear this plot? All items and trees (except special trees) will be returned to your inventory.')) {
                form.submit();
            }
            
            document.body.removeChild(form);
        });
    }
});