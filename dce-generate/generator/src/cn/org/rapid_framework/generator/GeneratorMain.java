package cn.org.rapid_framework.generator;


/**
 * 
 * @author badqiu
 * @email badqiu(a)gmail.com
 */

public class GeneratorMain {
	/**
	 * ��ֱ���޸����´�����ò�ͬ�ķ�����ִ�������������.
	 */
	public static void main(String[] args) throws Exception {
		GeneratorFacade g = new GeneratorFacade();
//		g.printAllTableNames();				//��ӡ���ݿ��еı�����
		
		g.clean();							//ɾ�������������Ŀ¼
		g.generateByTable("resources");	//ͨ�����ݿ�������ļ�,ע��: oracle ��Ҫָ��schema��ע������Ĵ�Сд.
//		g.generateByAllTable();				//�Զ��������ݿ��е����б������ļ�
//		g.generateByClass(Blog.class);
		
		//���ļ���
		Runtime.getRuntime().exec("cmd.exe /c start D:\\output");
	}
}
