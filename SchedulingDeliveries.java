
// Copy paste this Java Template and save it as "SchedulingDeliveries.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0102800A
// write your name here: Suranjana Sengupta
// write list of collaborators here: Akshat Dubey
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class SchedulingDeliveries {

	BinaryHeap bh;

	public SchedulingDeliveries() {
		bh = new BinaryHeap();
	}

	void ArriveAtHospital(String womanName, int dilation) { // INSERT ITEM
		Woman mother = new Woman();
		mother.name = womanName;
		mother.dilation = dilation;
		bh.Insert(mother);

	}

	void UpdateDilation(String womanName, int increaseDilation) { // UPDATE ITEM

		Woman mother = new Woman();
		mother.name = womanName;
		mother.dilation = increaseDilation;
		bh.Update(mother);

	}

	void GiveBirth(String womanName) { // DELETE ITEM

		bh.ExtractMax();

	}

	String Query() { // RETURN HEAD OF QUEUE
		String ans;
		ans = bh.Query();

		return ans;
	}

	void run() throws Exception {
		// do not alter this method

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >=
														// N
		while (numCMD-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int command = Integer.parseInt(st.nextToken());
			switch (command) {
			case 0:
				ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken()));
				break;
			case 1:
				UpdateDilation(st.nextToken(), Integer.parseInt(st.nextToken()));
				break;
			case 2:
				GiveBirth(st.nextToken());
				break;
			case 3:
				pr.println(Query());
				break;
			}
		}
		// bh.print();
		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		SchedulingDeliveries ps1 = new SchedulingDeliveries();
		ps1.run();
	}
}

class Woman {

	int dilation;
	String name;
	int order; // order of insertion into heap
	int index;

	Woman() {
		dilation = 0;
		name = "";
		order = 0;
		index = 0;
	}
}

class BinaryHeap {
	private Vector<Woman> A;
	private Map<String, Woman> B = new HashMap<String, Woman>();
	private int BinaryHeapSize;
	int binaryHeapOrder;

	BinaryHeap() {
		A = new Vector<Woman>();
		A.add(new Woman());
		BinaryHeapSize = 0;
		binaryHeapOrder = 0;
	}

	int parent(int i) {
		return i >> 1;
	} // shortcut for i/2, round down

	int left(int i) {
		return i << 1;
	} // shortcut for 2*i

	int right(int i) {
		return (i << 1) + 1;
	} // shortcut for 2*i + 1

	void shiftUp(int i) {
		Woman temp = A.get(i);
		Woman temp2;
		temp.index = i;
		while (i > 1 && A.get(parent(i)).dilation <= A.get(i).dilation) {
			if (A.get(parent(i)).dilation == A.get(i).dilation && A.get(parent(i)).order < A.get(i).order) {
				break;
			}

			temp = A.get(i);
			temp2 = A.get(parent(i));
			temp.index = parent(i);
			temp2.index = i;
			A.set(i, temp2);
			A.set(parent(i), temp);
			i = parent(i);

		}

	}

	void Insert(Woman mother) {
		BinaryHeapSize++;
		binaryHeapOrder++;
		mother.order = binaryHeapOrder;
		if (BinaryHeapSize >= A.size()) {
			A.add(mother);
			B.put(mother.name, mother);
		} else {
			A.set(BinaryHeapSize, mother);
			B.put(mother.name, mother);
			
		}

		shiftUp(BinaryHeapSize);
//		print();
	}

	void Update(Woman mother) {
		Woman temp;
		int i = searchByName(mother.name);
		temp = A.get(i);
		temp.dilation = A.get(i).dilation + mother.dilation;
		temp.name = A.get(i).name;
		temp.order = A.get(i).order;
		A.set(i, temp);
		shiftUp(i);
		shiftDown(i);

	}

	void Delete(String motherName) {
		int i = searchByName(motherName);
		A.set(i, A.get(BinaryHeapSize));
		A.remove(BinaryHeapSize);
		B.remove(motherName);
		BinaryHeapSize--;

		if ((i - 1) != BinaryHeapSize) {
			shiftUp(i);
			shiftDown(i);
		}

	}

	String Query() {
		if (BinaryHeapSize > 0)
			return A.get(1).name;
		else
			return "The delivery suite is empty";
	}

	int searchByName(String motherName) {
		return B.get(motherName).index;
	}

	void print() {
		System.out.println();
		System.out.println();
		System.out.println("PRINTING MAP ELEMENTS");
		for (Map.Entry<String, Woman> entry : B.entrySet()) {
			System.out.println(entry.getKey() + "/" + entry.getValue().index);
		}
		System.out.println();

		System.out.println("PRINTING VECTOR ELEMENTS");
		for (int i = 0; i < A.size(); i++) {
			System.out.println(i + " " + A.get(i).name + " " + A.get(i).index + " " + A.get(i).dilation);
		}

		System.out.println();
		System.out.println();

	}

	void shiftDown(int i) {
		Woman temp = A.get(i);
		Woman temp2;
		temp.index = i;
		boolean isLeft = true;
		boolean isRight = true;

		while (i <= BinaryHeapSize) {
			isLeft = true;
			isRight = true;
			
			int maxV = A.get(i).dilation, max_id = i;
			if (left(i) <= BinaryHeapSize && maxV <= A.get(left(i)).dilation) {
				if (maxV == A.get(left(i)).dilation) {
					if (A.get(i).order < A.get(left(i)).order)
						isLeft = false;
				}
				if (isLeft) {
					maxV = A.get(left(i)).dilation;
					max_id = left(i);
				}
			}

			if (right(i) <= BinaryHeapSize && maxV <= A.get(right(i)).dilation) {
				if (maxV == A.get(right(i)).dilation) {
					if (A.get(i).order < A.get(right(i)).order)
						isRight = false;
				}
				if (isRight) {
					maxV = A.get(right(i)).dilation;
					max_id = right(i);
				}
			}

			if (max_id != i) {
				temp = A.get(i);
				temp2 = A.get(max_id);
				temp.index = max_id;
				temp2.index = i;
				A.set(i, temp2);
				A.set(max_id, temp);
				i = max_id;
			} else
				break;
			
			
		}
	}

	Woman ExtractMax() {
		Woman maxV = A.get(1);
		A.set(1, A.get(BinaryHeapSize));
		BinaryHeapSize--; // virtual decrease
		shiftDown(1);
		return maxV;
	}

	void BuildBinaryHeap(Woman[] array) { // the O(n) version, array is 0-based
		BinaryHeapSize = array.length;
		A = new Vector<Woman>();
		A.add(new Woman()); // dummy, this BinaryHeap is 1-based
		for (int i = 1; i <= BinaryHeapSize; i++) // copy the content
			A.add(array[i - 1]);
		for (int i = parent(BinaryHeapSize); i >= 1; i--)
			shiftDown(i);
	}

	Vector<Woman> BinaryHeapSort(Woman[] array) {
		BuildBinaryHeap(array);
		int N = array.length;
		for (int i = 1; i <= N; i++)
			A.set(N - i + 1, ExtractMax());
		return A; // ignore the first index 0
	}

	int size() {
		return BinaryHeapSize;
	}

	boolean isEmpty() {
		return BinaryHeapSize == 0;
	}
}
