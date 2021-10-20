window.addEventListener('load', function () {

    const formulario = document.querySelector('#add_new_paciente');
    formulario.addEventListener('submit', function (event) {
        let pacienteDomicilioArray = document.querySelector('#domicilio').value.split(',');
        let pacienteLocalidad = pacienteDomicilioArray[1];
        let pacienteProvincia = pacienteDomicilioArray[2];
        let domicilioEntero = pacienteDomicilioArray[0];
        let domicilioNro = domicilioEntero.match(/\d+/g)[0];
        let pacienteCalle = domicilioEntero.substr(0, domicilioEntero.lastIndexOf(domicilioNro))
        event.preventDefault();
        const formData = {
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            dni: document.querySelector('#dni').value,
            fechaIngreso: document.querySelector('#fechaIngreso').value,
            domicilio: {
                calle: pacienteCalle,
                numero: domicilioNro,
                localidad: pacienteLocalidad,
                provincia: pacienteProvincia
            }
        };

        const url = '/pacientes';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                 let successAlert = '<div class="alert alert-success alert-dismissible">' +
                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                     '<strong></strong> Paciente agregado </div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();

            })
            .catch(error => {
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                     '<strong> Error intente nuevamente</strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                     resetUploadForm();})
    });

    function resetUploadForm(){
        document.querySelector('#nombre').value = "";
        document.querySelector('#apellido').value = "";
        document.querySelector('#dni').value = "";
        document.querySelector('#fechaIngreso').value = "";
        document.querySelector('#domicilio').value = "";
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/pacientesList.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});