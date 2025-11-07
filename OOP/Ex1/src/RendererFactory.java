/**
 * Factory class to create Renderer objects based on type.
 * @author einavye
 */
class RendererFactory{
    /**
     * Constructor for RendererFactory
     */
    public RendererFactory(){}
    /**
     * Builds a Renderer object based on the given type.
     * @param type the type of renderer to create ("void", "console")
     * @param size the size of the board (used for console renderer)
     * @return a Renderer object of the specified type, or null if the type is unrecognized
     */
    public Renderer buildRenderer(String type, int size){
        Renderer renderer = null;
        if(type.equals("void")){
            renderer = new VoidRenderer();
        } else if (type.equals("console")) {
            renderer = new ConsoleRenderer(size);
        }
        return renderer;
    }
}