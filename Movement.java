public enum Movement {
	U (0, -1, "1"),
	UR (-1, -1, "2"),
	R (1, 0, "3"),
	DR (-1, 1, "4"),
	D (0, 1, "5"),
	DL (1, 1, "6"),
	L (-1, 0, "7"),
	UL (1, -1, "8"),
	S (0, 0, "9");

	public final int x;
	public final int y;
	public final String id;

	Movement (int x, int y, String id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
}

	