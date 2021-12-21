function validation() {
	
	const forms = document.getElementsByClassName('.needs-validation');
	
	Array.prototype.slice.call(forms)
	.forEach((form) => {
		fpr.addEventListener('submit', (event) => {
			if(!form.checkValidity()) {
				event.preventDefault()
          		event.stopPropagation()
			}
			
			form.classList.add('was-validated');
		}, false);
	});
	
}