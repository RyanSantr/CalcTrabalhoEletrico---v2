from collections import deque
from pathlib import Path

try:
    from PIL import Image
except ImportError as exc:
    raise SystemExit("Instale Pillow para usar este script: pip install pillow") from exc


ROOT = Path(__file__).resolve().parents[1]
RAW_DIR = ROOT / "src" / "resources" / "assets" / "raw"
OUT_DIR = ROOT / "src" / "resources" / "assets"

FILES = {
    "hero_character.png": "hero_character.png",
    "header_bar.png": "header_bar.png",
    "input_panel.png": "input_panel.png",
    "configuration_panel.png": "configuration_panel.png",
    "formula_panel.png": "formula_panel.png",
    "result_panel.png": "result_panel.png",
}


def is_fake_background(r, g, b, a):
    if a < 24:
        return True

    bright = r > 232 and g > 232 and b > 232
    checker_gray = abs(r - g) < 5 and abs(g - b) < 5 and 205 <= r <= 246
    almost_white = r > 242 and g > 242 and b > 242
    return bright or checker_gray or almost_white


def clean_image(source: Path, target: Path):
    image = Image.open(source).convert("RGBA")
    pixels = image.load()
    width, height = image.size
    visited = set()
    queue = deque()

    for x in range(width):
        queue.append((x, 0))
        queue.append((x, height - 1))
    for y in range(height):
        queue.append((0, y))
        queue.append((width - 1, y))

    while queue:
        x, y = queue.popleft()
        if (x, y) in visited or x < 0 or y < 0 or x >= width or y >= height:
            continue
        visited.add((x, y))

        r, g, b, a = pixels[x, y]
        if not is_fake_background(r, g, b, a):
            continue

        pixels[x, y] = (r, g, b, 0)
        queue.extend(((x + 1, y), (x - 1, y), (x, y + 1), (x, y - 1)))

    target.parent.mkdir(parents=True, exist_ok=True)
    image.save(target)
    print(f"ok: {source.name} -> {target}")


def main():
    for raw_name, out_name in FILES.items():
        source = RAW_DIR / raw_name
        if source.exists():
            clean_image(source, OUT_DIR / out_name)
        else:
            print(f"skip: {source} nao existe")


if __name__ == "__main__":
    main()
