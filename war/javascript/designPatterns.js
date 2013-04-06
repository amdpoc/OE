// *** ALL CLASSES are from Pro JavaScript Design Patterns by Ross Harmes and Dustin Diaz

// *** INTERFACE CONSTRUCTOR
var Interface = function(name, methods)
{
    if (arguments.length != 2)
    {
        throw new Error ("Interface constructor called with " + arguments.length + " arguments instead of expected 2.");
    }

    this.name = name;
    this.methods = [];
    for (var i = 0, iL = methods.length; i < iL; i++)
    {
        if (typeof methods[i] !== "string")
        {
            throw new Error ("Interface constructor expects method names to be passed in as a string.");
        }
        this.methods.push(methods[i]);
    }
};

// *** INTERFACE STATIC CLASS METHOD
Interface.ensureImplements = function(object)
{
    console.log("ensureImplements :: object = ",object, "getEnvelope = ",object.getEnvelope());
    if (arguments.length < 2)
    {
        throw new Error ("Function Interface.ensureImplements called with " + arguments.length + " arguments instead of expected 2.");
    }

    for (var i = 1, iL = arguments.length; i < iL; i++)
    {
        var interface = arguments[i];
        if (interface.constructor !== Interface)
        {
            throw new Error ("Function Interface.ensureImplements expects arguments two and above to be instances of Interface.");
        }

        for (var j = 0, jL = interface.methods.length; j < jL; j++)
        {
            var method = interface.methods[j];
            if (!object[method] || typeof object[method] !== 'function')
            {
                throw new Error ("Function Interface.ensureImplements: object does not implement the " + interface.name + " interface. Method " + method + " was not found.");
            }
        }
    }
};

// *** INHERITANCE:

// *** CLASSICAL - extend method
function extend(subClass, superClass)
{
    var F = function() {};
    F.prototype = superClass.prototype;
    subClass.prototype = new F();
    subClass.prototype.constructor = subClass;
    subClass.superclass = superClass.prototype;
    if(superClass.prototype.constructor == Object.prototype.constructor)
    {
        superClass.prototype.constructor = superClass;
    }
};

// *** PROTOTYPAL - clone method
function clone(object)
{
    function F() {}
    F.prototype = object;
    return new F;
};