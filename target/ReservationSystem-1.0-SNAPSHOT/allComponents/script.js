$(document).ready(function() {
    $('#customerTable').DataTable({
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'pdfHtml5',
                text: 'Generate PDF',
                title: 'Customer Details',
                className: 'btn btn-primary', // Apply Bootstrap primary button class
                customize: function (doc) {
                    doc.styles.tableHeader = {
                        bold: true,
                        color: 'black',
                        fillColor: '#d9edf7',
                        alignment: 'center'
                    };
                    doc.defaultStyle.alignment = 'center';
                    doc.content[1].table.widths = ['20%', '30%', '30%', '20%'];
                }
            }
        ]
    });
});

$(document).ready(function () {
    $('#reservationsTable').DataTable({
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'pdfHtml5',
                text: 'Generate PDF',
                title: 'Reservations Details',
                className: 'btn btn-primary', // Apply navy blue button
                customize: function (doc) {
                    doc.styles.tableHeader = {
                        bold: true,
                        color: 'white',
                        fillColor: '#001f3f', // Navy blue for header
                        alignment: 'center'
                    };
                }
            }
        ]
    });
});
