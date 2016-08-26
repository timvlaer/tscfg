// generated by tscfg 0.3.3 on Thu Aug 25 22:26:32 PDT 2016
// source: example/def.example.conf

package tscfg.example;

public class JavaExampleCfg {
  public final Endpoint endpoint;
  public static class Endpoint {
    public final int intReq;
    public final Interface interface_;
    public static class Interface {
      public final int port;
      public final String type;
      public Interface(com.typesafe.config.Config c) {
        this.port = c != null && c.hasPathOrNull("port") ? c.getInt("port") : 8080;
        this.type = c != null && c.hasPathOrNull("type") ? c.getString("type") : null;
      }
      public String toString() { return toString(""); }
      public String toString(String i) {
        return i+ "port = " + this.port + "\n"
            +i+ "type = " + (this.type == null ? null : '"' + this.type + '"') + "\n";
      }
    }
    public final String path;
    public final Integer serial;
    public final String url;
    public Endpoint(com.typesafe.config.Config c) {
      this.intReq = c.getInt("intReq");
      this.interface_ = new Interface(__$config(c, "interface"));
      this.path = c.getString("path");
      this.serial = c != null && c.hasPathOrNull("serial") ? Integer.valueOf(c.getInt("serial")) : null;
      this.url = c != null && c.hasPathOrNull("url") ? c.getString("url") : "http://example.net";
    }
    public String toString() { return toString(""); }
    public String toString(String i) {
      return i+ "intReq = " + this.intReq + "\n"
          +i+ "interface_ {\n" + this.interface_.toString(i+"    ") +i+ "}\n"
          +i+ "path = " + '"' + this.path + '"' + "\n"
          +i+ "serial = " + this.serial + "\n"
          +i+ "url = " + '"' + this.url + '"' + "\n";
    }
  }
  public JavaExampleCfg(com.typesafe.config.Config c) {
    this.endpoint = new Endpoint(__$config(c, "endpoint"));
  }
  public String toString() { return toString(""); }
  public String toString(String i) {
    return i+ "endpoint {\n" + this.endpoint.toString(i+"    ") +i+ "}\n";
  }
  private static com.typesafe.config.Config __$config(com.typesafe.config.Config c, String path) {
    return c != null && c.hasPath(path) ? c.getConfig(path) : null;
  }
}