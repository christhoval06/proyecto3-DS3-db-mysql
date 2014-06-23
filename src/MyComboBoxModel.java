import modelos.Item;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class MyComboBoxModel extends DefaultComboBoxModel<Item> implements ComboBoxModel<Item> {
	private static final long serialVersionUID = 1L;
	private List<Item> items;
	private Object selection = null;

	public MyComboBoxModel(List<Item> _items) {
		items = _items;
	}

	public MyComboBoxModel(ArrayList<Item> _items) {
		items = _items;
	}

	@Override
	public int getSize() {
		return items.size();
	}

	@Override
	public Item getElementAt(int index) {
		return items.get(index);
	}

    public Item getElementWithId(String id){
        Item item = items.get(0);
        for (Item i : items){
            if(i.getCode().equalsIgnoreCase(id)){
                item=i;
                break;
            }
        }
        return item;
    }

	@Override
	public void setSelectedItem(Object anItem) {
		if (anItem == null || items.contains(anItem)) {
			selection = anItem;
			fireContentsChanged(this, -1, -1);
		}
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

}
