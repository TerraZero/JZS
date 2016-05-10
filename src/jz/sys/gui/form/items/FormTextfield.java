package jz.sys.gui.form.items;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import jz.sys.events.JZEHandler;
import jz.sys.gui.components.JZTextfield;
import jz.sys.gui.form.TZFormItem;

public class FormTextfield extends TZFormItem<FormTextfield> {
	
	public JZEHandler eAction;
	
	protected JZTextfield text;
	
	public FormTextfield(String name, String ui) {
		super(name, ui);
		this.text = new JZTextfield().name(name + ":text").ui(ui + ":text").parent(this.panel);
		this.eAction = new JZEHandler("form:textfield:action");
		
		this.text.addActionListener((e) -> {
			FormTextfield.this.eAction.fire("action", e, FormTextfield.this);
		});
		
		this.text.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				FormTextfield.this.text.setSelectionStart(0);
				FormTextfield.this.text.setSelectionEnd(FormTextfield.this.text.getText().length());
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				FormTextfield.this.text.setSelectionStart(0);
				FormTextfield.this.text.setSelectionEnd(0);
			}
			
		});
	}
	
	@Override
	public void arrange() {
		super.arrange();
		this.text.setSize(this.width(), this.height());
	}

	@Override
	public String value() {
		return this.text.value();
	}

	@Override
	public FormTextfield value(String value) {
		this.text.value(value);
		return this;
	}

	@Override
	public String type() {
		return "textfield";
	}

}
