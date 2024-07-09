$(function() {
    $("#book-search").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: "/searchBooks",
                data: {
                    query: request.term
                },
                success: function(data) {
                    response($.map(data, function(item) {
                        return {
                            label: item.name,
                            value: item.name,
                            id: item.id
                        };
                    }));
                }
            });
        },
        select: function(event, ui) {
            window.location.href = '/book?id=' + ui.item.id;
        },
        minLength: 2
    });
});

function toggleButtonContainer() {
    var container = document.getElementById('buttonContainer');
    container.classList.toggle('show');
}
