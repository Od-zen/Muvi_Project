package jp.co.sss.shop.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterForm {

    // ユーザーID
    @NotNull(message = "ユーザーIDを入力してください。")
    @Max(value = 999, message = "ユーザーIDは999以下で入力してください。")
    private Integer userId;

    // メールアドレス
    @NotBlank(message = "メールアドレスを入力してください。")
    @Email(message = "正しいメールアドレスの形式で入力してください。")
    private String email;

    // メールアドレス確認用
    @NotBlank(message = "確認用メールアドレスを入力してください。")
    private String emailConfirm;

    // パスワード
    @NotBlank(message = "パスワードを入力してください。")
    @Size(max = 16, message = "パスワードは16文字以内で入力してください。")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "パスワードは半角英数字で入力してください。")
    private String password;

    // パスワード確認用
    @NotBlank(message = "確認用パスワードを入力してください。")
    private String passwordConfirm;

    // 郵便番号（123-4567 または 1234567）
    @NotBlank(message = "郵便番号を入力してください。")
    @Pattern(
        regexp = "^\\d{3}-?\\d{4}$",
        message = "郵便番号は「123-4567」または「1234567」の形式で入力してください。"
    )
    private String zip;

    // ----- getter / setter -----
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEmailConfirm() { return emailConfirm; }
    public void setEmailConfirm(String emailConfirm) { this.emailConfirm = emailConfirm; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPasswordConfirm() { return passwordConfirm; }
    public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm = passwordConfirm; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }
}