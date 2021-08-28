
package erg;

public class Otmac {

    private enum ReturnType {
        VIEW, DETAILS, DEVICE
    };

    public static DetailsWidget detailsWidget(Device dev, AppController parent) {
        return (DetailsWidget) reflectionMagic(dev, parent, ReturnType.DETAILS, null);
    }

    public static ViewWidget viewWidget(Device dev, AppController parent) {
        return (ViewWidget) reflectionMagic(dev, parent, ReturnType.VIEW, null);
    }

    public static Device device(String className, String room_name) {
        return (Device) reflectionMagic(null, null, ReturnType.DEVICE, room_name);
    }

    private static Object reflectionMagic(Device dev, AppController parent, ReturnType type, String room_name) {
        var class_name = dev.getClass().getSimpleName();
        /*
         * HACK: initialize as null, if it remains null we don't case since a
         * RuntimeException is gonna be thrown.
         */
        Object result = null;

        switch (class_name) {
        case "Lamp":
            switch (type) {
            case VIEW:
                result = new LampViewWidget(dev, parent);
                break;
            case DETAILS:
                result = new LampDetailsWidget(dev, parent);
                break;
            case DEVICE:
                result = new Lamp(room_name);
                break;
            }
            break;
        case "Thermostat":
            switch (type) {
            case VIEW:
                result = new TemperatureViewWidget(dev, parent);
                break;
            case DETAILS:
                result = new TemperatureDetailsWidget(dev, parent);
                break;
            case DEVICE:
                result = new Thermostat(room_name);
                break;
            }
            break;
        case "TV":
            switch (type) {
            case VIEW:
                result = new TVViewWidget(dev, parent);
                break;
            case DETAILS:
                result = new TVDetailsWidget(dev, parent);
                break;
            case DEVICE:
                result = new TV(room_name);
                break;
            }
            break;
        case "Radio":
            switch (type) {
            case VIEW:
                result = new RadioViewWidget(dev, parent);
                break;
            case DETAILS:
                result = new RadioDetailsWidget(dev, parent);
                break;
            case DEVICE:
                result = new Radio(room_name);
                break;
            }
            break;
        default:
            throw new RuntimeException("Class '" + class_name + "' is not supported by Otmac.");
        }

        if (result == null) {
            throw new RuntimeException("Class '" + class_name + "' is not supported by Otmac. Also nice one.");
        }

        return result;
    }
}
