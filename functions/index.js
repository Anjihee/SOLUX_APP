/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const {onRequest} = require("firebase-functions/v2/https");
const logger = require("firebase-functions/logger");

// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();

const nodemailer = require("nodemailer");

exports.sendEmailVerificationCode = functions.https.onCall(async (data, context) => {
  const email = data.email;

  // Generate random verification code
  const verificationCode = Math.floor(100000 + Math.random() * 900000).toString();

  try {
    await admin.auth().updateUserByEmail(email, {
      emailVerified: false,
      customClaims: {
        verificationCode: verificationCode,
      },
    });

    // Send verification code via email
    const transporter = nodemailer.createTransport({
      service: "gmail",
      auth: {
        user: "YOUR_GMAIL_USERNAME",
        pass: "YOUR_GMAIL_PASSWORD",
      },
    });

    const mailOptions = {
      from: "surround@gmail.com",
      to: email,
      subject: "Email Verification Code",
      text: `Your verification code is: ${verificationCode}`,
    };

    await transporter.sendMail(mailOptions);

    return { success: true, message: "Verification code sent successfully." };
  } catch (error) {
    console.error(error);
    return { success: false, message: "Failed to send verification code." };
  }
});
