package by.asushenya.sela.bean;

public class Ower {
	User user;
	Equipment[] equipments;

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Equipment getEquipments(int index) {
		return equipments[index];
	}
	public Equipment[] getEquipments(){
		return equipments;
	}
	public void setEquipments(Equipment[] equipments) {
		this.equipments = new Equipment[equipments.length];
		System.arraycopy(equipments, 0, this.equipments, 0, equipments.length);
	}
	public void setEquipments(int index, Equipment value){
		this.equipments[index] = value;
	}
}
