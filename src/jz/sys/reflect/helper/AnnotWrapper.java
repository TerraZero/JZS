package jz.sys.reflect.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import jz.sys.reflect.loader.ReflectFile;
import jz.sys.reflect.loader.ReflectLoader;

public class AnnotWrapper<annot extends Annotation> {

	private List<Annot<annot>> annots;
	
	public AnnotWrapper(Class<annot> annot) {
		this.annots = new ArrayList<Annot<annot>>();

		for (ReflectFile file : ReflectLoader.sysloader().load()) {
			if (file.reflect().hasAnnotation(annot)) {
				this.annots.add(new Annot<annot>(file.reflect(), annot));
			}
		}
	}
	
	public List<Annot<annot>> getAnnots() {
		return this.annots;
	}
	
}
