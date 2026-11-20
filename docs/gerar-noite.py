"""
Gera a versão noturna das imagens de cenário (Spiral Atlas, que só tem dia).

Para cada imagem indicada, cria "<nome>-noite.jpg" na mesma pasta, com:
  - brilho reduzido
  - dessaturação leve (noite tem menos cor)
  - tom puxado para o azul
  - vinheta (cantos mais escuros, como luz de poste)

Uso:
  python gerar-noite.py                 -> gera para as imagens da LISTA
  python gerar-noite.py --todas         -> gera para todas as imagens do pacote
"""

import os
import sys

from PIL import Image, ImageDraw, ImageEnhance, ImageFilter

CENARIOS = os.path.join(
    os.path.dirname(os.path.abspath(__file__)),
    "..", "src", "main", "resources", "view", "img", "cenario")

# Imagens usadas (ou previstas) na história.
LISTA = [
    "casa/entrance.jpg",
    "casa/hallway.jpg",
    "casa/lounge.jpg",
    "rua/street.jpg",
    "rua/alley.jpg",
    "praca/park.jpg",
    "praca/smallpark.jpg",
    "armazem/warehouse_control.jpg",
    "armazem/warehouse_floor.jpg",
    "diversos/cell.jpg",
    "diversos/doctor.jpg",
    "diversos/powerhill.jpg",
]

BRILHO = 0.46


def graduar(im):
    im = ImageEnhance.Brightness(im.convert("RGB")).enhance(BRILHO)
    im = ImageEnhance.Color(im).enhance(0.6)

    r, g, b = im.split()
    r = r.point(lambda v: min(255, int(v * 0.84)))
    g = g.point(lambda v: min(255, int(v * 0.94)))
    b = b.point(lambda v: min(255, int(v * 1.14)))
    im = Image.merge("RGB", (r, g, b))

    largura, altura = im.size
    mascara = Image.new("L", (largura, altura), 0)
    ImageDraw.Draw(mascara).ellipse(
        (-largura * 0.22, -altura * 0.30, largura * 1.22, altura * 1.30), fill=255)
    mascara = mascara.filter(ImageFilter.GaussianBlur(largura // 14))
    return Image.composite(im, Image.new("RGB", (largura, altura), (0, 0, 0)), mascara)


def arquivos(todas):
    if todas:
        for pasta in sorted(os.listdir(CENARIOS)):
            caminho_pasta = os.path.join(CENARIOS, pasta)
            if not os.path.isdir(caminho_pasta):
                continue
            for arquivo in sorted(os.listdir(caminho_pasta)):
                if arquivo.lower().endswith((".jpg", ".jpeg")) and "-noite" not in arquivo:
                    yield os.path.join(pasta, arquivo)
    else:
        yield from LISTA


def main():
    for rel in arquivos("--todas" in sys.argv):
        origem = os.path.normpath(os.path.join(CENARIOS, rel))
        if not os.path.exists(origem):
            print("faltando:", rel)
            continue
        destino = os.path.splitext(origem)[0] + "-noite.jpg"
        graduar(Image.open(origem)).save(destino, quality=90)
        print("gerado:", os.path.relpath(destino, CENARIOS))


if __name__ == "__main__":
    main()
