package jz.sys.net.udp;

public class UDPTest {

	public static void main(String[] args) {
		test2();
	}
	
	public static void test1() {
		UDPSocket s = UDPSocket.create("test", 51973);
		s.bind();
		UDPSocket ss = UDPSocket.create("test2", 51937);
		ss.bind();
		s.send("hallo", "localhost", 51937);
		UDPData d = ss.receive();
		System.out.println(d.string());
		System.out.println(d.port());
		System.out.println(d.ip());
	}
	
	public static void test2() {
		final UDPSocket s = UDPSocket.create("test", 51973);
		s.bind();
		s.timeout(0);
		Thread t = new Thread() {
			
			@Override
			public void run() {
				System.out.println("rec");
				UDPData d = s.receive();
				System.out.println(d.string());
			}
			
		};
		t.start();
		
		for (int i = 0; i < 100000; i++) {
			
		}
		System.out.println("send");
		s.send("Hallo", "localhost", 51973);
		System.out.println("ok");
		s.unbind();
	}
	
}
