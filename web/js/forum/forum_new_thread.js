document.addEventListener('DOMContentLoaded', function() {
    const uploadArea = document.getElementById('uploadArea');
    const fileInput = document.getElementById('imageUpload');
    const previewContainer = document.getElementById('imagePreviewContainer');
    const form = document.getElementById('newThreadForm');
    
    // Display server-side error messages
    const urlParams = new URLSearchParams(window.location.search);
    const errorMessage = urlParams.get('error');
    if (errorMessage) {
        alert(errorMessage); // Display error as an alert
    }
    
    // Handle drag and drop events
    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
        uploadArea.addEventListener(eventName, preventDefaults, false);
    });
    
    function preventDefaults(e) {
        e.preventDefault();
        e.stopPropagation();
    }
    
    // Add visual feedback when dragging files over the upload area
    ['dragenter', 'dragover'].forEach(eventName => {
        uploadArea.addEventListener(eventName, () => {
            uploadArea.classList.add('dragover');
        });
    });
    
    ['dragleave', 'drop'].forEach(eventName => {
        uploadArea.addEventListener(eventName, () => {
            uploadArea.classList.remove('dragover');
        });
    });
    
    // Handle dropped files
    uploadArea.addEventListener('drop', function(e) {
        const droppedFiles = e.dataTransfer.files;
        handleFiles(droppedFiles);
    });
    
    // Handle file selection via file input
    uploadArea.addEventListener('click', function(e) {
        // Don't trigger click if clicking on the label or child elements of the label
        const target = e.target;
        if (target.tagName.toLowerCase() === 'label' || 
            target.closest('label')) {
            return;
        }
        fileInput.click();
    });
    
    fileInput.addEventListener('change', function() {
        handleFiles(this.files);
    });
    
    // Process selected files
    function handleFiles(files) {
        if (!files.length) return;
        
        // Create a new FileList-like object
        const dataTransfer = new DataTransfer();
        
        // Add existing files if any (keep already uploaded files)
        if (fileInput.files && fileInput.files.length > 0) {
            Array.from(fileInput.files).forEach(file => {
                dataTransfer.items.add(file);
            });
        }
        
        // Filter for images and add to preview
        Array.from(files).forEach(file => {
            if (!file.type.match('image.*')) return;
            
            dataTransfer.items.add(file);
            
            const reader = new FileReader();
            
            reader.onload = function(e) {
                const preview = document.createElement('div');
                preview.className = 'image-preview';
                
                const img = document.createElement('img');
                img.src = e.target.result;
                
                const removeBtn = document.createElement('div');
                removeBtn.className = 'remove-image';
                removeBtn.innerHTML = '<i class="bi bi-x"></i>'; // Updated remove button icon
                
                removeBtn.addEventListener('click', function(e) {
                    e.stopPropagation();
                    removeImage(preview, file);
                });
                
                preview.appendChild(img);
                preview.appendChild(removeBtn);
                previewContainer.appendChild(preview);
            };
            
            reader.readAsDataURL(file);
        });
        
        // Update the file input with all files
        fileInput.files = dataTransfer.files;
    }
    
    // Remove image from preview and file list
    function removeImage(previewElement, fileToRemove) {
        // Remove preview element
        previewElement.remove();
        
        // Remove from file input
        const dt = new DataTransfer();
        const { files } = fileInput;
        
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            // Skip the file we want to remove
            if (file !== fileToRemove) {
                dt.items.add(file);
            }
        }
        
        // Update the file input
        fileInput.files = dt.files;
    }
    
    // Form validation
    form.addEventListener('submit', function(e) {
        let isValid = true;
        
        // Validate title
        const titleInput = document.getElementById('threadTitle');
        if (!titleInput.value.trim()) {
            markInvalid(titleInput, 'Please enter a title');
            isValid = false;
        } else {
            clearInvalid(titleInput);
        }
        
        // Validate description
        const descInput = document.getElementById('threadDescription');
        if (!descInput.value.trim()) {
            markInvalid(descInput, 'Please enter a description');
            isValid = false;
        } else {
            clearInvalid(descInput);
        }
        
        if (!isValid) {
            e.preventDefault();
        }
    });
    
    function markInvalid(element, message) {
        const formGroup = element.closest('.form-group');
        formGroup.classList.add('error');
        
        // Remove any existing error message
        const existingError = formGroup.querySelector('.error-message');
        if (existingError) {
            existingError.remove();
        }
        
        // Add error message
        const errorMsg = document.createElement('div');
        errorMsg.className = 'error-message';
        errorMsg.textContent = message;
        formGroup.appendChild(errorMsg);
    }
    
    function clearInvalid(element) {
        const formGroup = element.closest('.form-group');
        formGroup.classList.remove('error');
        
        const existingError = formGroup.querySelector('.error-message');
        if (existingError) {
            existingError.remove();
        }
    }
});