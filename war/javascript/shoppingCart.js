// *** Created by okotlia 04/03/2013
var sc;

ShoppingCart_Class = function()
{
    this.init();
};

ShoppingCart_Class.prototype.init = function()
{
    this.aProducts = [];
    console.log("shopping cart object created.....");
};

ShoppingCart_Class.prototype.addProduct = function(oProduct)
{
    this.aProducts.push(oProduct);
    console.log("shopping cart object created.....");
};

ShoppingCart_Class.prototype.removeProduct = function(oProduct)
{
    var that = this;
    this.aProducts.splice($.inArray(oProduct,that.aProducts), 1);
    console.log("shopping cart object created.....");
};