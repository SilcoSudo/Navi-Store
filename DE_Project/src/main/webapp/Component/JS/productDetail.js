$(document).ready(function () {
  function formatPrice(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
  }
  $(".product-price").each(function () {
    var priceText = $(this).text().replace(" ₫", "");
    var formattedPrice = formatPrice(priceText);
    $(this).text(formattedPrice + " ₫");
  });
  $("#increment").on("click", function () {
    let currentVal = parseInt($("#quantity").val());
    $("#quantity").val(currentVal + 1);
  });

  $("#decrement").on("click", function () {
    let currentVal = parseInt($("#quantity").val());
    if (currentVal > 1) {
      $("#quantity").val(currentVal - 1);
    }
  });
  $("#btnAddToCart_details").on("click", function () {
    var quantity = parseInt($("#quantity").val());
    var productID = $("#btnAddToCart_details").data("product-id");
    $.ajax({
      url: `${contextPath}/Product/AddToCart`,
      method: "POST",
      data: {
        quantity: quantity,
        productID: productID,
      },
      success: function () {
        var currentCount = parseInt($("#cart-count").text());
        $("#cart-count").text(currentCount + quantity);
        alert("Đã thêm sản phẩm thành công.");
      },
      error: function (response) {
        alert(response.responseText);
      },
    });
  });
});