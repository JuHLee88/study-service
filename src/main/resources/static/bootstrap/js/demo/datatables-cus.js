// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable({
    "pageLength":1,
    "tabIndex": 1,
    "searching": false,
    "pagingType":"simple",
    "language": {
      "info": "",
      "lengthMenu": "",
      "search": ""
    }
  });
});
