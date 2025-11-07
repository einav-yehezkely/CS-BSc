class RendererFactory{
    public RendererFactory(){}

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