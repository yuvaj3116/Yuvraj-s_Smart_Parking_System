package com.yuvraj;

import java.util.Scanner;

//Parking Slot Class
class ParkingSlot {
	int slotNumber;
	boolean isOccupied;
	Vehicle vehicle; // store vehicle info

 ParkingSlot(int slotNumber) {
     this.slotNumber = slotNumber;
     this.isOccupied = false;
     this.vehicle = null;
 }

 void occupySlot(Vehicle v) {
     isOccupied = true;
     vehicle = v;
 }

 void freeSlot() {
     isOccupied = false;
     vehicle = null;
 }
}

//Vehicle Class
class Vehicle {
	String numberPlate;
	long entryTime; // store entry time

 Vehicle(String numberPlate) {
     this.numberPlate = numberPlate;
     this.entryTime = System.currentTimeMillis();
 	}
}

//Parking Lot Class
class ParkingLot {
	ParkingSlot[] slots;

 ParkingLot(int totalSlots) {
     slots = new ParkingSlot[totalSlots];
     for (int i = 0; i < totalSlots; i++) {
         slots[i] = new ParkingSlot(i + 1);
     }
 }

 void parkVehicle(Vehicle v) {
     for (ParkingSlot slot : slots) {
         if (!slot.isOccupied) {
             slot.occupySlot(v);
             System.out.println("✅ Vehicle " + v.numberPlate + " parked at slot " + slot.slotNumber);
             return;
         }
     }
     System.out.println("❌ No empty slots available!");
 }

 void leaveSlot(int slotNumber) {
     if (slotNumber > 0 && slotNumber <= slots.length) {
         ParkingSlot slot = slots[slotNumber - 1];
         if (slot.isOccupied) {
             long exitTime = System.currentTimeMillis();
             long parkedMillis = exitTime - slot.vehicle.entryTime;
             int parkedHours = (int) Math.ceil(parkedMillis / (1000.0 * 60 * 60)); // convert ms → hrs

             int charges = calculateCharges(parkedHours);

             System.out.println("\n🚗 Vehicle " + slot.vehicle.numberPlate + " is leaving Slot " + slotNumber);
             System.out.println("⏳ Parked Time: " + parkedHours + " hour(s)");
             System.out.println("💰 Parking Charges: ₹" + charges);

             slot.freeSlot();
         } else {
             System.out.println("⚠️ Slot " + slotNumber + " is already empty.");
         }
     } else {
         System.out.println("Invalid slot number!");
     }
 }

 int calculateCharges(int hours) {
     if (hours <= 3) {
         return 50; // flat for first 3 hrs
     } else {
         return 50 + (hours - 3) * 15; // 50 + extra
     }
 }

 void displaySlots() {
     System.out.println("\n--- Parking Slot Status ---");
     for (ParkingSlot slot : slots) {
         if (slot.isOccupied) {
             System.out.println("Slot " + slot.slotNumber + " -> Occupied by " + slot.vehicle.numberPlate);
         } else {
             System.out.println("Slot " + slot.slotNumber + " -> Free");
         	}
     	}
 	}
}

//Main Class
public class SmartParkingSystem {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ParkingLot lot = new ParkingLot(5); // Example: 5 slots

		while (true) {
			System.out.println("\n==== Smart Parking Menu ====");
			System.out.println("1. Park Vehicle");
			System.out.println("2. Leave Slot");
			System.out.println("3. Display Slots");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();

			switch (choice) {
             	case 1:
             		System.out.print("Enter Vehicle Number Plate: ");
             		String numberPlate = sc.next();
             		Vehicle v = new Vehicle(numberPlate);
             		lot.parkVehicle(v);
             		break;

             	case 2:
             		System.out.print("Enter Slot Number to free: ");
             		int slotNumber = sc.nextInt();
             		lot.leaveSlot(slotNumber);
             		break;

	            case 3:
	                lot.displaySlots();
	                break;
	
	            case 4:
	                System.out.println("Exiting... Thank you!");
	                sc.close();
	                return;
	
	            default:
	                System.out.println("Invalid choice! Try again.");
         	}
     	}
 	}
}

