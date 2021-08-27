
package erg;

public class Otmac {

    private enum WidgetType {
        VIEW,
        DETAILS
    };

    public static DetailsWidget detailsWidget(Device dev, AppController parent) {
        return (DetailsWidget) reflectionMagic(dev, parent, WidgetType.DETAILS);
    }

    public static ViewWidget viewWidget(Device dev, AppController parent) {
        return (ViewWidget) reflectionMagic(dev, parent, WidgetType.VIEW);
    }

    private static CustomWidget reflectionMagic(Device dev, AppController parent, WidgetType type) {
        var class_name = dev.getClass().getSimpleName();
        /*
         *  HACK: initialize as null,
         * if it remains null we don't case since a RuntimeException is gonna be thrown.
         */
        CustomWidget widget = null; 

        switch (class_name) {
        case "Lamp":
            switch(type) {
            case VIEW:
                widget = new LampViewWidget(dev, parent);
                break;
            case DETAILS:
                widget = new LampDetailsWidget(dev, parent);
                break;
            }
            break;
        case "Thermostat":
            switch(type) {
            case VIEW:
                widget = new TemperatureViewWidget(dev, parent);
                break;
            case DETAILS:
                widget = new TemperatureDetailsWidget(dev, parent);
                break;
            }
            break;
        case "TV":
            switch(type) {
            case VIEW:
            widget = new TVViewWidget(dev, parent);
                break;
            case DETAILS:
            widget = new TVDetailsWidget(dev, parent);
                break;
        // case "Radio":
        //     switch(type) {
        //     case VIEW:
        //     widget = new RadioViewWidget(dev, parent);
        //         break;
        //     case DETAILS:
        //     widget = new RadioDetailsWidget(dev, parent);
        //         break;
            default:
                throw new RuntimeException("Class '" + class_name + "' is not supported by Otmac.");
            }
        }

        return widget;
    }
}
