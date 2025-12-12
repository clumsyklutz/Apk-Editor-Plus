# Instruções para Publicar no GitHub

## Passo 1: Criar o Repositório no GitHub

1. Acesse https://github.com e faça login na sua conta
2. Clique no botão "+" no canto superior direito e selecione "New repository"
3. Configure o repositório:
   - **Nome**: `ApkEditorPlus` (ou outro nome de sua preferência)
   - **Descrição**: "Projeto open source de editor de APK para Android, decompilado e refatorado para Kotlin"
   - **Visibilidade**: Selecione **Public** (público)
   - **NÃO** marque "Initialize this repository with a README" (já temos um)
   - **NÃO** adicione .gitignore ou LICENSE (já temos)
4. Clique em "Create repository"

## Passo 2: Conectar o Repositório Local ao GitHub

Após criar o repositório, o GitHub mostrará instruções. Execute os seguintes comandos no terminal (substitua `SEU_USUARIO` pelo seu nome de usuário do GitHub):

```bash
cd C:\Users\produ\AndroidStudioProjects\ApkEditorPlus
git remote add origin https://github.com/SEU_USUARIO/ApkEditorPlus.git
git branch -M main
git push -u origin main
```

**OU** se você preferir usar SSH (se tiver configurado):

```bash
git remote add origin git@github.com:SEU_USUARIO/ApkEditorPlus.git
git branch -M main
git push -u origin main
```

## Passo 3: Verificar

Após o push, acesse seu repositório no GitHub e verifique se todos os arquivos foram enviados corretamente.

## Notas

- Se você já tiver um repositório com o nome `ApkEditorPlus`, use um nome diferente
- Se solicitado, faça login no GitHub quando o Git pedir credenciais
- O repositório será público e visível para todos

