package jz.sys.reflect.annot;

import java.lang.annotation.Annotation;

import jz.sys.reflect.Reflect;

public class Annot<annot extends Annotation> extends Reflect {

	private Class<annot> annotation;
	private annot annot;
	
	public Annot(Reflect reflect, Class<annot> annotation) {
		super(reflect);
		this.annotation = annotation;
		this.annot = reflect.annotation(annotation);
	}
	
	public annot getAnnot() {
		return this.annot;
	}
	
	public Class<annot> getAnnotType() {
		return this.annotation;
	}
	
}
